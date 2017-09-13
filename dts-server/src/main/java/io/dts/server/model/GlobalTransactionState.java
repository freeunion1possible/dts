package io.dts.server.model;

/**
 * @author jiangyu.jy
 * 
 *         全局事务状态
 */
public enum GlobalTransactionState {

  /**
   * 开始
   */
  Begin(1),

  /**
   * 已提交
   */
  Committed(2),

  /**
   * 已回滚
   */
  Rollbacked(3),

  /**
   * 提交中
   */
  Committing(4),

  /**
   * 回滚中
   */
  Rollbacking(5),

  /**
   * Commit未决状态（同步到slave失败）
   */
  CommitHeuristic(6);

  /**
   * @param value
   */
  private GlobalTransactionState(int value) {
    this.value = value;
  }

  /**
   * @return
   */
  public int getValue() {
    return value;
  }

  /**
   * 状态值
   */
  private int value;

  public static GlobalTransactionState parse(int value) {
    for (GlobalTransactionState state : GlobalTransactionState.values()) {
      if (state.getValue() == value) {
        return state;
      }
    }
    return null;
  }
}
