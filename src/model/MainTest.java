package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.misc.TransactionManager;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.SelectArg;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class MainTest {

	private final static String DATABASE_URL = "jdbc:h2:mem:UserTest";

	private Dao<UserTest, Integer> UserTestDao;

	public static void main(String[] args) throws Exception {
		// turn our static method into an instance of Main
		new MainTest().doMain(args);
	}

	private void doMain(String[] args) throws Exception {
		ConnectionSource connectionSource = null;
		try {
			// create our data-source for the database
			connectionSource = new JdbcConnectionSource(DATABASE_URL);
			// setup our database and DAOs
			setupDatabase(connectionSource);
			// read and write some data
			readWriteData();
			// do a bunch of bulk operations
			readWriteBunch();
			// show how to use the SelectArg object
			useSelectArgFeature();
			// show how to use the SelectArg object
			useTransactions(connectionSource);
			System.out.println("\n\nIt seems to have worked\n\n");
		} finally {
			// destroy the data source which should close underlying connections
			if (connectionSource != null) {
				connectionSource.close();
			}
		}
	}

	/**
	 * Setup our database and DAOs
	 */
	private void setupDatabase(ConnectionSource connectionSource) throws Exception {

		UserTestDao = DaoManager.createDao(connectionSource, UserTest.class);

		// if you need to create the table
		TableUtils.createTable(connectionSource, UserTest.class);
	}

	/**
	 * Read and write some example data.
	 */
	private void readWriteData() throws Exception {
		// create an instance of UserTest
		String name = "Jim Coakley";
		UserTest UserTest = new UserTest(name);

		// persist the UserTest object to the database
		UserTestDao.create(UserTest);
		int id = UserTest.getFunctionalRegistration();
		verifyDb(id, UserTest);

		// update the database after changing the object
		UserTestDao.update(UserTest);
		verifyDb(id, UserTest);

		// query for all items in the database
		List<UserTest> UserTests = UserTestDao.queryForAll();
		assertEquals("Should have found 1 UserTest matching our query", 1, UserTests.size());
		verifyUserTest(UserTest, UserTests.get(0));

		// loop through items in the database
		int UserTestC = 0;
		for (UserTest UserTest2 : UserTestDao) {
			verifyUserTest(UserTest, UserTest2);
			UserTestC++;
		}
		assertEquals("Should have found 1 UserTest in for loop", 1, UserTestC);

		// construct a query using the QueryBuilder
		QueryBuilder<UserTest, Integer> statementBuilder = UserTestDao.queryBuilder();
		// shouldn't find anything: name LIKE 'hello" does not match our UserTest
		statementBuilder.where().like(model.UserTest.NAME_FIELD_NAME, "hello");
		UserTests = UserTestDao.query(statementBuilder.prepare());
		assertEquals("Should not have found any UserTests matching our query", 0, UserTests.size());

		// should find our UserTest: name LIKE 'Jim%' should match our UserTest
		statementBuilder.where().like(model.UserTest.NAME_FIELD_NAME, name.substring(0, 3) + "%");
		UserTests = UserTestDao.query(statementBuilder.prepare());
		assertEquals("Should have found 1 UserTest matching our query", 1, UserTests.size());
		verifyUserTest(UserTest, UserTests.get(0));

		// delete the UserTest since we are done with it
		UserTestDao.delete(UserTest);
		// we shouldn't find it now
		assertNull("UserTest was deleted, shouldn't find any", UserTestDao.queryForId(id));
	}

	/**
	 * Example of reading and writing a large(r) number of objects.
	 */
	private void readWriteBunch() throws Exception {

		Map<String, UserTest> UserTests = new HashMap<String, UserTest>();
		for (int i = 1; i <= 100; i++) {
			String name = Integer.toString(i);
			UserTest UserTest = new UserTest(name);
			// persist the UserTest object to the database, it should return 1
			UserTestDao.create(UserTest);
			UserTests.put(name, UserTest);
		}

		// query for all items in the database
		List<UserTest> all = UserTestDao.queryForAll();
		assertEquals("Should have found same number of UserTests in map", UserTests.size(), all.size());
		for (UserTest UserTest : all) {
			assertTrue("Should have found UserTest in map", UserTests.containsValue(UserTest));
			verifyUserTest(UserTests.get(UserTest.getName()), UserTest);
		}

		// loop through items in the database
		int UserTestC = 0;
		for (UserTest UserTest : UserTestDao) {
			assertTrue("Should have found UserTest in map", UserTests.containsValue(UserTest));
			verifyUserTest(UserTests.get(UserTest.getName()), UserTest);
			UserTestC++;
		}
		assertEquals("Should have found the right number of UserTests in for loop", UserTests.size(), UserTestC);
	}

	/**
	 * Example of created a query with a ? argument using the {@link SelectArg} object. You then can set the value of
	 * this object at a later time.
	 */
	private void useSelectArgFeature() throws Exception {

		String name1 = "foo";
		String name2 = "bar";
		String name3 = "baz";
		assertEquals(1, UserTestDao.create(new UserTest(name1)));
		assertEquals(1, UserTestDao.create(new UserTest(name2)));
		assertEquals(1, UserTestDao.create(new UserTest(name3)));

		QueryBuilder<UserTest, Integer> statementBuilder = UserTestDao.queryBuilder();
		SelectArg selectArg = new SelectArg();
		// build a query with the WHERE clause set to 'name = ?'
		statementBuilder.where().like(UserTest.NAME_FIELD_NAME, selectArg);
		PreparedQuery<UserTest> preparedQuery = statementBuilder.prepare();

		// now we can set the select arg (?) and run the query
		selectArg.setValue(name1);
		List<UserTest> results = UserTestDao.query(preparedQuery);
		assertEquals("Should have found 1 UserTest matching our query", 1, results.size());
		assertEquals(name1, results.get(0).getName());

		selectArg.setValue(name2);
		results = UserTestDao.query(preparedQuery);
		assertEquals("Should have found 1 UserTest matching our query", 1, results.size());
		assertEquals(name2, results.get(0).getName());

		selectArg.setValue(name3);
		results = UserTestDao.query(preparedQuery);
		assertEquals("Should have found 1 UserTest matching our query", 1, results.size());
		assertEquals(name3, results.get(0).getName());
	}

	/**
	 * Example of created a query with a ? argument using the {@link SelectArg} object. You then can set the value of
	 * this object at a later time.
	 */
	private void useTransactions(ConnectionSource connectionSource) throws Exception {
		String name = "trans1";
		final UserTest UserTest = new UserTest(name);
		assertEquals(1, UserTestDao.create(UserTest));

		TransactionManager transactionManager = new TransactionManager(connectionSource);
		try {
			// try something in a transaction
			transactionManager.callInTransaction(new Callable<Void>() {
				@Override
				public Void call() throws Exception {
					// we do the delete
					assertEquals(1, UserTestDao.delete(UserTest));
					assertNull(UserTestDao.queryForId(UserTest.getFunctionalRegistration()));
					// but then (as an example) we throw an exception which rolls back the delete
					throw new Exception("We throw to roll back!!");
				}
			});
			fail("This should have thrown");
		} catch (SQLException e) {
			// expected
		}

		assertNotNull(UserTestDao.queryForId(UserTest.getFunctionalRegistration()));
	}

	/**
	 * Verify that the UserTest stored in the database was the same as the expected object.
	 */
	private void verifyDb(int id, UserTest expected) throws SQLException, Exception {
		// make sure we can read it back
		UserTest UserTest2 = UserTestDao.queryForId(id);
		if (UserTest2 == null) {
			throw new Exception("Should have found id '" + id + "' in the database");
		}
		verifyUserTest(expected, UserTest2);
	}

	/**
	 * Verify that the UserTest is the same as expected.
	 */
	private static void verifyUserTest(UserTest expected, UserTest UserTest2) {
		assertEquals("expected name does not equal UserTest name", expected, UserTest2);
	}
}
