/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.jackrabbit.oak.segment.standby.codec;

import java.util.List;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GetBlobRequestDecoder extends MessageToMessageDecoder<String> {

    private static final Logger log = LoggerFactory.getLogger(GetBlobRequestDecoder.class);

    @Override
    protected void decode(ChannelHandlerContext ctx, String msg, List<Object> out) throws Exception {
        String request = Messages.extractMessageFrom(msg);

        if (request != null && request.startsWith(Messages.GET_BLOB)) {
            log.debug("Parsed 'get blob' message");
            out.add(new GetBlobRequest(Messages.extractClientFrom(msg), request.substring(Messages.GET_BLOB.length())));
        } else {
            ctx.fireChannelRead(msg);
        }
    }

}