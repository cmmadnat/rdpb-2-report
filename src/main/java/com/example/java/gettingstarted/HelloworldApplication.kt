/*
 * Copyright 2015 Google Inc. All Rights Reserved.
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

package com.example.java.gettingstarted


import com.example.java.gettingstarted.model.StudyCenter
import com.example.java.gettingstarted.model.StudyCenterService
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder
import net.sf.dynamicreports.report.builder.DynamicReports
import net.sf.dynamicreports.report.builder.DynamicReports.*
import net.sf.dynamicreports.report.constant.PageOrientation
import net.sf.dynamicreports.report.constant.PageType
import net.sf.dynamicreports.report.exception.DRException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.io.IOException
import javax.servlet.http.HttpServletResponse
import javax.swing.table.DefaultTableModel

@SpringBootApplication
@RestController
open class HelloworldApplication {
    @RequestMapping("/")
    fun home(): String {
        return "Hello World!"
    }

    internal var tableModel: DefaultTableModel? = null


    @RequestMapping("/report1")
    fun report(httpServletResponse: HttpServletResponse) {
        try {
            val list: MutableList<StudyCenter> = getAllSufficiency()
            val build = getReport1(list).toHtml(httpServletResponse.outputStream)
        } catch (e: DRException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    private fun getAllSufficiency(): MutableList<StudyCenter> {
        val list: MutableList<StudyCenter> = mutableListOf()
        var page = 0
        while (true) {
            val list2 = studyCenterService.findAllSufficiency(page++, 500)
            list.addAll(list2)
            if (list2.isEmpty()) break
        }
        return list
    }

    @RequestMapping("/report1/pdf")
    fun report1pdf(httpServletResponse: HttpServletResponse) {
        try {
            val list: MutableList<StudyCenter> = getAllSufficiency()
            val build = getReport1(list).toPdf(httpServletResponse.outputStream)
        } catch (e: DRException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    @RequestMapping("/report1/xls")
    fun report1xls(httpServletResponse: HttpServletResponse) {
        try {
            val list: MutableList<StudyCenter> = getAllSufficiency()
            val build = getReport1(list).toXlsx(httpServletResponse.outputStream)
        } catch (e: DRException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    private fun getReport1(sample: MutableList<StudyCenter>): JasperReportBuilder {
        sample.forEachIndexed { index: Int, studyCenter: StudyCenter -> studyCenter.index = index + 1 }
        return DynamicReports.report().columns(//add columns
                //             title,     field name     data type
                col.column("ลำดับ", "index", type.integerType()),
                col.column("ชื่อคน/ชื่อชุมชน/ชื่อกลุ่ม", "name", type.stringType()),
                col.column("ด้าน/ประเภท/ชนิด", "type", type.stringType()),
                col.column("รางวัล", "type", type.stringType()),
                col.column("ปีที่จัดตั้ง", "year", type.integerType()),
                col.column("พิกัดที่ตั้ง (Latitude/Longitude)", "location", type.stringType()),
                col.column("ที่อยู่", "address", type.stringType()),
                col.column("สถานะศูนย์ฯ", "status", type.stringType()),
                col.column("ข้อมูลด้านเศรษฐกิจพอเพียง/รายละเอียด", "detail", type.stringType()),
                col.column("ผู้ประสานงาน", "contact", type.stringType()),
                col.column("สถานะข้อมูล", "complete", type.stringType()))
                .setDataSource(sample)
                .title(cmp.text("ศูนย์เรียนรู้เศรษฐกิจพอเพียง")).setPageFormat(PageType.A4, PageOrientation.LANDSCAPE)
                .pageFooter(cmp.pageXofY())
    }

    /**
     * (Optional) App Engine health check endpoint mapping.
     *
     * @see [](https://cloud.google.com/appengine/docs/flexible/java/how-instances-are-managed.health_checking)
     * If your app does not handle health checks, a HTTP 404 response is interpreted
     * as a successful reply.
     */
    @RequestMapping("/_ah/health")
    fun healthy(): String {
        // Message body required though ignored
        return "Still surviving."
    }

    companion object {

        @JvmStatic
        fun main(args: Array<String>) {
            SpringApplication.run(HelloworldApplication::class.java, *args)
        }
    }

    @Autowired lateinit var studyCenterService: StudyCenterService
}
