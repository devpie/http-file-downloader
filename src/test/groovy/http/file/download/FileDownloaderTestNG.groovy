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

import org.testng.annotations.DataProvider
import org.testng.annotations.Test

/**
 *
 */
class FileDownloaderTestNG {

    String baseUrl = "http://repository.codehaus.org/org/codehaus/groovy/modules/http-builder/http-builder/0.6/"
    FileDownloader dl = new FileDownloader(baseUrl)

    @Test(dataProvider = "data")
    void shouldBe(Map d) {
        println "Downloading ${baseUrl}${d.src}"
        dl.download(d.src, d.dest).get()
    }

    @DataProvider(name = "data", parallel = true)
    Object[][] provide() {
        def baseLocal = new File("/tmp/${System.nanoTime()}/")
        baseLocal.mkdirs()
        [
                [[
                        src: "http-builder-0.6-all.tar.gz.md5",
                        dest: new File(baseLocal, "http-builder-0.6-all.tar.gz.md5")
                ]].toArray(),
                [[
                        src: "http-builder-0.6-all.tar.gz.sha1",
                        dest: new File(baseLocal, "http-builder-0.6-all.tar.gz.sha1")
                ]].toArray(),
                [[
                        src: "http-builder-0.6-javadoc.jar",
                        dest: new File(baseLocal, "http-builder-0.6-javadoc.jar")
                ]].toArray()
        ].toArray()
    }
}

