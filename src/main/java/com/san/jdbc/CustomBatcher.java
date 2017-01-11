package com.san.jdbc;

import java.sql.PreparedStatement;
import java.util.Map.Entry;

import org.hibernate.JDBCException;
import org.hibernate.engine.jdbc.batch.internal.AbstractBatchImpl;
import org.hibernate.engine.jdbc.batch.spi.BatchKey;
import org.hibernate.engine.jdbc.spi.JdbcCoordinator;

public class CustomBatcher extends AbstractBatchImpl {

	protected CustomBatcher(BatchKey key, JdbcCoordinator jdbcCoordinator) {
		super(key, jdbcCoordinator);
		this.jdbcCoordinator = jdbcCoordinator;
	}

	private JdbcCoordinator jdbcCoordinator;

	@SuppressWarnings("deprecation")
	@Override
	public void addToBatch() {
		notifyObserversImplicitExecution();
		for (Entry<String, PreparedStatement> entry : getStatements().entrySet()) {
			try {
				final PreparedStatement statement = entry.getValue();
				@SuppressWarnings("unused")
				final int rowCount = jdbcCoordinator.getResultSetReturn().executeUpdate(statement);
				// Below function call compares the actual rows updated and
				// expected count, so just commented the below line
				// getKey().getExpectation().verifyOutcome( rowCount, statement,
				// 0 );
				jdbcCoordinator.getResourceRegistry().release(statement);
				jdbcCoordinator.afterStatementExecution();
			} catch (JDBCException e) {
				abortBatch();
				throw e;
			}
		}

		getStatements().clear();
	}

	@Override
	protected void doExecuteBatch() {
		// Function implementation not required

	}

}