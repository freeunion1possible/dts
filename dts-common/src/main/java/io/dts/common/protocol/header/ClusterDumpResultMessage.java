/*
 * Copyright 2014-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package io.dts.common.protocol.header;

import io.dts.common.protocol.ResponseMessage;
import io.dts.remoting.CommandCustomHeader;
import io.dts.remoting.exception.RemotingCommandException;

/**
 * @author liushiming
 * @version ClusterDumpResultMessage.java, v 0.0.1 2017年9月4日 下午2:32:17 liushiming
 */
public class ClusterDumpResultMessage implements CommandCustomHeader, ResponseMessage {

  private boolean result;

  private String msg;

  public boolean isResult() {
    return result;
  }

  public void setResult(boolean result) {
    this.result = result;
  }

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }

  @Override
  public void checkFields() throws RemotingCommandException {

  }


}
