package io.dts.datasource;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import io.dts.parser.struct.DatabaseType;
import io.dts.resourcemanager.BaseResourceManager;
import io.dts.resourcemanager.ResourceManager;
import io.dts.resourcemanager.helper.DataSourceHolder;

/**
 * Created by guoyubo on 2017/9/20.
 */
public class DtsDataSource extends AbstractDtsDataSource {

  private DataSource dataSource;

  private String dbName;

  private ResourceManager resourceManager;


  public DtsDataSource(final DataSource dataSource) {
    this.dataSource = dataSource;
    try {
      this.dbName = dataSource.getConnection().getSchema();
      DataSourceHolder.registerDataSource(dbName, dataSource);
    } catch (SQLException e) {
    }
  }

  public void setResourceManager(final ResourceManager resourceManager) {
    this.resourceManager = resourceManager;
  }

  public DatabaseType getDatabaseType() throws SQLException {
    try (Connection connection = dataSource.getConnection()) {
      return DatabaseType.valueFrom(connection.getMetaData().getDatabaseProductName());
    }
  }

  @Override
  public Connection getConnection() throws SQLException {
    return new DtsConnection(this, dataSource.getConnection());
  }

  @Override
  public Connection getConnection(final String username, final String password)
      throws SQLException {
    return new DtsConnection(this, dataSource.getConnection(username, password));
  }

  public DataSource getRawDataSource() {
    return dataSource;
  }

  @Override
  public String getDbName() {
    return dbName;
  }

  public ResourceManager getResourceManager() {
    if (resourceManager == null) {
      resourceManager = BaseResourceManager.getInstance("io.dts.resourcemanager.RtResourceManager");
    }
    return resourceManager;
  }

}
