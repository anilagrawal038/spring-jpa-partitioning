package com.san.jdbc;

import org.hibernate.engine.jdbc.batch.internal.BatchBuilderImpl;
import org.hibernate.engine.jdbc.batch.internal.BatchingBatch;
import org.hibernate.engine.jdbc.batch.spi.Batch;
import org.hibernate.engine.jdbc.batch.spi.BatchKey;
import org.hibernate.engine.jdbc.spi.JdbcCoordinator;

public class CustomBatchBuilder extends BatchBuilderImpl {

	private static final long serialVersionUID = 6635773251556307026L;

	public CustomBatchBuilder(int jdbcBatchSize) {
		super(jdbcBatchSize);
		this.jdbcBatchSize = jdbcBatchSize;
	}

	public CustomBatchBuilder() {
		super(fetchBatchSize());
		this.jdbcBatchSize = tmpBatchSize;
	}

	private static int fetchBatchSize() {
		tmpBatchSize = 1; // Default value
		// If you want to use batch sized provided in configuration using
		// property hibernate.jdbc.batch_size
		// Then get that value and assign here
		return tmpBatchSize;
	}

	private int jdbcBatchSize;

	private static int tmpBatchSize;

	@Override
	public Batch buildBatch(BatchKey key, JdbcCoordinator jdbcCoordinator) {
		final Integer sessionJdbcBatchSize = jdbcCoordinator.getJdbcSessionOwner().getJdbcBatchSize();
		final int jdbcBatchSizeToUse = sessionJdbcBatchSize == null ? this.jdbcBatchSize : sessionJdbcBatchSize;
		return jdbcBatchSizeToUse > 1 ? new BatchingBatch(key, jdbcCoordinator, jdbcBatchSizeToUse)
				: new CustomBatcher(key, jdbcCoordinator);
	}
}
