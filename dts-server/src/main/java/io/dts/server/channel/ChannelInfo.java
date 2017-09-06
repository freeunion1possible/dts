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
package io.dts.server.channel;

import io.dts.remoting.protocol.LanguageCode;
import io.netty.channel.Channel;

/**
 * @author liushiming
 * @version ChannelInfo.java, v 0.0.1 2017年9月6日 上午10:26:50 liushiming
 */
public class ChannelInfo {
  private final Channel channel;
  private final String clientId;
  private final LanguageCode language;
  private final int version;
  private volatile long lastUpdateTimestamp = System.currentTimeMillis();

  public ChannelInfo(Channel channel) {
    this(channel, null, null, 0);
  }

  public ChannelInfo(Channel channel, String clientId, LanguageCode language, int version) {
    this.channel = channel;
    this.clientId = clientId;
    this.language = language;
    this.version = version;
  }

  public Channel getChannel() {
    return channel;
  }

  public String getClientId() {
    return clientId;
  }

  public LanguageCode getLanguage() {
    return language;
  }


  public int getVersion() {
    return version;
  }


  public long getLastUpdateTimestamp() {
    return lastUpdateTimestamp;
  }

  public void setLastUpdateTimestamp(long lastUpdateTimestamp) {
    this.lastUpdateTimestamp = lastUpdateTimestamp;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((channel == null) ? 0 : channel.hashCode());
    result = prime * result + ((clientId == null) ? 0 : clientId.hashCode());
    result = prime * result + ((language == null) ? 0 : language.hashCode());
    result = prime * result + (int) (lastUpdateTimestamp ^ (lastUpdateTimestamp >>> 32));
    result = prime * result + version;
    return result;
  }


  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    ChannelInfo other = (ChannelInfo) obj;
    if (channel == null) {
      if (other.channel != null)
        return false;
    } else if (this.channel != other.channel) {
      return false;
    }
    return true;
  }


  @Override
  public String toString() {
    return "ChannelInfo [channel=" + channel + ", clientId=" + clientId + ", language=" + language
        + ", version=" + version + ", lastUpdateTimestamp=" + lastUpdateTimestamp + "]";
  }
}
