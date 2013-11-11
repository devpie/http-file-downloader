/**
 * Copyright 2013 Jan Marco Müller <jan.marco.mueller@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package http.file.download

import groovyx.net.http.AsyncHTTPBuilder
import groovyx.net.http.HttpResponseDecorator

/**
 * An http file downloader.
 */
class FileDownloader {
    private String m_BaseUrl
    private AsyncHTTPBuilder m_Http

    FileDownloader(final String baseUrl) {
        m_BaseUrl = baseUrl
        unifyBaseUrl()
        m_Http = new AsyncHTTPBuilder([
                poolSize: 10,
                uri: m_BaseUrl])
    }

    private def unifyBaseUrl() {
        if (!m_BaseUrl.endsWith("/")) {
            m_BaseUrl += "/"
        }
    }

    def download(final String srcUrl, final File dest) {
        m_Http.get([path: srcUrl]) { HttpResponseDecorator resp ->
            if((resp.status / 100).intValue() != 2) {
                return
            }
            dest.withOutputStream { out ->
                    resp.entity.writeTo out
            }
        }
    }

}
