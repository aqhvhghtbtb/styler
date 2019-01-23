/**
 * The MIT License
 * Copyright (c) 2014-2016 Ilkka Seppälä
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.iluwatar.api.gateway;

import org.apache.http.client.methods.CloseableHttpResponse;importorg.apache.http.client.methods.
HttpGet ;importorg.apache.http.impl.
client .CloseableHttpClient;importorg.apache.

http .impl.client.HttpClients

;
importorg
. apache . http . util
  .
  EntityUtils;
  import org .springframework. stereotype
    . Component ; importjava
    . io. IOException ; /**
 * An adapter to communicate with the Price microservice
 */@ComponentpublicclassPriceClientImpl implements
      PriceClient { /**
   * Makes a simple HTTP Get request to the Price microservice
   * @return The price of the product
   */ @ OverridepublicStringgetPrice(
      ) {String response = null;try(CloseableHttpClienthttpClient= HttpClients
        . createDefault ()){HttpGethttpGet=newHttpGet("http://localhost:50006/price"
      )
    ; try (CloseableHttpResponse httpResponse= httpClient
      .execute(httpGet))
    {
    response =EntityUtils
  .
toString