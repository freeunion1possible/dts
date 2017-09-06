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
package io.dts.server;

import java.util.concurrent.ExecutorService;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.dts.common.protocol.RequestCode;
import io.dts.remoting.RemotingServer;
import io.dts.remoting.netty.NettyRemotingServer;
import io.dts.remoting.netty.NettyRequestProcessor;
import io.dts.remoting.netty.NettyServerConfig;
import io.dts.server.channel.ChannelHeatBeatProcessor;
import io.dts.server.channel.ChannelRepository;
import io.dts.server.channel.ChannelkeepingListener;
import io.dts.server.processor.SingleMessageProcessor;
import io.dts.server.processor.MultipleMessageProcessor;

/**
 * @author liushiming
 * @version TcpServerController.java, v 0.0.1 2017年9月6日 上午10:06:17 liushiming
 */
@Component
public class TcpServerController {

  @Autowired
  private TcpServerProperties tcpServerProperties;

  private final ChannelRepository channelRepository;

  private final ChannelkeepingListener channelKeepingListener;

  private RemotingServer remotingServer;

  public TcpServerController() {
    this.channelRepository = ChannelRepository.newChannelRepository();
    this.channelKeepingListener = ChannelkeepingListener.newChannelkeepingListener(this);
  }

  @PostConstruct
  public void init() {
    NettyServerConfig nettyServerConfig = new NettyServerConfig();
    nettyServerConfig.setListenPort(tcpServerProperties.getListenPort());
    this.remotingServer = new NettyRemotingServer(nettyServerConfig, channelKeepingListener);
    this.registerProcessor();
  }

  private void registerProcessor() {
    SingleMessageProcessor addProcessor = new SingleMessageProcessor(this, tcpServerProperties);
    MultipleMessageProcessor getProcessor = new MultipleMessageProcessor(this, tcpServerProperties);
    ChannelHeatBeatProcessor clientManageProcessor =
        new ChannelHeatBeatProcessor(this, tcpServerProperties);
    registerProcessor(1, addProcessor, addProcessor.getThreadPool());
    registerProcessor(2, getProcessor, getProcessor.getThreadPool());
    registerProcessor(RequestCode.HEART_BEAT, clientManageProcessor,
        clientManageProcessor.getThreadPool());
  }

  private void registerProcessor(int processorCode, NettyRequestProcessor processor,
      ExecutorService processorThreadPool) {
    this.remotingServer.registerProcessor(processorCode, processor, processorThreadPool);
  }

  public void start() {
    if (this.remotingServer != null) {
      this.remotingServer.start();
    }
    if (this.channelKeepingListener != null) {
      this.channelKeepingListener.start();
    }
  }

  @PreDestroy
  public void shutdown() {
    if (this.channelKeepingListener != null) {
      this.channelKeepingListener.shutdown();
    }
    if (this.remotingServer != null) {
      this.remotingServer.shutdown();
    }
  }

  public final ChannelRepository getChannelRepository() {
    return channelRepository;
  }

}
