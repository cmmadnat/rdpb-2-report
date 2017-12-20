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
import net.sf.dynamicreports.report.constant.HorizontalTextAlignment
import net.sf.dynamicreports.report.constant.ListType
import net.sf.dynamicreports.report.constant.PageOrientation
import net.sf.dynamicreports.report.constant.PageType
import net.sf.dynamicreports.report.exception.DRException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.awt.Color
import java.io.IOException
import javax.servlet.http.HttpServletResponse


@SpringBootApplication
@RestController
open class HelloworldApplication {

    @RequestMapping("/")
    fun home(): String {
        return "Hello World!"
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

    private fun getAllRoyalty(): MutableList<StudyCenter> {
        val list: MutableList<StudyCenter> = mutableListOf()
        var page = 0
        while (true) {
            val list2 = studyCenterService.findAllRoyal(page++, 500)
            list.addAll(list2)
            if (list2.isEmpty()) break
        }
        return list
    }

    private fun getAllOther(): MutableList<StudyCenter> {
        val list: MutableList<StudyCenter> = mutableListOf()
        var page = 0
        while (true) {
            val list2 = studyCenterService.findAllOther(page++, 500)
            list.addAll(list2)
            if (list2.isEmpty()) break
        }
        return list
    }

    @RequestMapping("/report1")
    fun report(httpServletResponse: HttpServletResponse) {
        try {
            val list: MutableList<StudyCenter> = getAllSufficiency()
            httpServletResponse.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_HTML.toString())
            getReport1(list).ignorePagination().toHtml(httpServletResponse.outputStream)
        } catch (e: DRException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }


    @RequestMapping("/report1/pdf")
    fun report1pdf(httpServletResponse: HttpServletResponse) {
        try {
            val list: MutableList<StudyCenter> = getAllSufficiency()
            httpServletResponse.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF.type)
            getReport1(list)
                    .pageFooter(cmp.pageXofY())
                    .toPdf(httpServletResponse.outputStream)
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
            httpServletResponse.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"report.xlsx\"")
            getReport1(list).ignorePagination().toXlsx(httpServletResponse.outputStream)
        } catch (e: DRException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    // report 2
    @RequestMapping("/report2")
    fun report2(httpServletResponse: HttpServletResponse) {
        try {
            val list: MutableList<StudyCenter> = getAllRoyalty()
            httpServletResponse.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_HTML.toString())
            getReport2(list).ignorePagination().toHtml(httpServletResponse.outputStream)
        } catch (e: DRException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }


    @RequestMapping("/report2/pdf")
    fun report2pdf(httpServletResponse: HttpServletResponse) {
        try {
            val list: MutableList<StudyCenter> = getAllRoyalty()
            httpServletResponse.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF.type)
            getReport2(list)
                    .pageFooter(cmp.pageXofY())
                    .toPdf(httpServletResponse.outputStream)
        } catch (e: DRException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    @RequestMapping("/report2/xls")
    fun report2xls(httpServletResponse: HttpServletResponse) {
        try {
            val list: MutableList<StudyCenter> = getAllRoyalty()
            httpServletResponse.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"report.xlsx\"")
            getReport2(list).ignorePagination().toXlsx(httpServletResponse.outputStream)
        } catch (e: DRException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    // report 3
    @RequestMapping("/report3")
    fun report3(httpServletResponse: HttpServletResponse) {
        try {
            val list: MutableList<StudyCenter> = getAllOther()
            httpServletResponse.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_HTML.toString())
            getReport3(list).ignorePagination().toHtml(httpServletResponse.outputStream)
        } catch (e: DRException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }


    @RequestMapping("/report3/pdf")
    fun report3pdf(httpServletResponse: HttpServletResponse) {
        try {
            val list: MutableList<StudyCenter> = getAllOther()
            httpServletResponse.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF.type)
            getReport3(list)
                    .pageFooter(cmp.pageXofY())
                    .toPdf(httpServletResponse.outputStream)
        } catch (e: DRException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    @RequestMapping("/report3/xls")
    fun report3xls(httpServletResponse: HttpServletResponse) {
        try {
            val list: MutableList<StudyCenter> = getAllOther()
            httpServletResponse.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"report.xlsx\"")
            getReport3Single(list).ignorePagination().toXlsx(httpServletResponse.outputStream)
        } catch (e: DRException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    private val SARABUN = "Sarabun"

    private fun getReport1(sample: MutableList<StudyCenter>): JasperReportBuilder {
        val fontName = stl.style().setFontName(SARABUN).setFontSize(12)
        val header = stl.style().setFontName(SARABUN).bold().setFontSize(18)
        val center = stl.style().setHorizontalTextAlignment(HorizontalTextAlignment.CENTER)

        val boldStyle = stl.style(fontName).bold()
        val boldCenteredStyle = stl.style(boldStyle)
                .setHorizontalTextAlignment(HorizontalTextAlignment.CENTER)
        val columnTitleStyle = stl.style(boldCenteredStyle)
                .setBorder(stl.pen1Point())
                .setBackgroundColor(Color.LIGHT_GRAY)
        sample.forEachIndexed { index: Int, studyCenter: StudyCenter -> studyCenter.index = index + 1 }
        return DynamicReports.report().columns(//add columns
                //             title,     field name     data type
                col.column("ลำดับ", "index", type.integerType()).setStyle(center),
                col.column("ชื่อคน/ชื่อชุมชน/ชื่อกลุ่ม", "name", type.stringType()),
                col.column("ด้าน/ประเภท/ชนิด", "type", type.stringType()),
                col.column("รางวัล", "award", type.stringType()),
                col.column("ปีที่จัดตั้ง", "year", type.integerType()),
                col.column("พิกัดที่ตั้ง (Latitude/Longitude)", "location", type.stringType()),
                col.column("ที่อยู่", "address", type.stringType()),
                col.column("สถานะศูนย์ฯ", "status", type.stringType()),
                col.column("ข้อมูลด้านเศรษฐกิจพอเพียง/รายละเอียด", "detail", type.stringType()),
                col.column("ผู้ประสานงาน", "contact", type.stringType()),
                col.column("สถานะข้อมูล", "complete", type.stringType()))
                .setDataSource(sample)
                .setTextStyle(fontName)
                .setColumnTitleStyle(columnTitleStyle)
                .highlightDetailEvenRows()
                .title(cmp.text("ศูนย์เรียนรู้เศรษฐกิจพอเพียง").setStyle(header)).setPageFormat(PageType.A4, PageOrientation.LANDSCAPE)
    }

    private fun getReport2(sample: MutableList<StudyCenter>): JasperReportBuilder {
        val fontName = stl.style().setFontName(SARABUN).setFontSize(12)
        val header = stl.style().setFontName(SARABUN).bold().setFontSize(18)
        val center = stl.style().setHorizontalTextAlignment(HorizontalTextAlignment.CENTER)

        val boldStyle = stl.style(fontName).bold()
        val boldCenteredStyle = stl.style(boldStyle)
                .setHorizontalTextAlignment(HorizontalTextAlignment.CENTER)
        val columnTitleStyle = stl.style(boldCenteredStyle)
                .setBorder(stl.pen1Point())
                .setBackgroundColor(Color.LIGHT_GRAY)
        sample.forEachIndexed { index: Int, studyCenter: StudyCenter -> studyCenter.index = index + 1 }
        return DynamicReports.report().columns(//add columns
                //             title,     field name     data type
                col.column("ลำดับ", "index", type.integerType()).setStyle(center),
                col.column("ชื่อกลุ่ม/เกษตรกร", "name", type.stringType()),
                col.column("ศูนย์ศึกษาการพัฒนา/พื้นที่", "organization", type.stringType()),
                col.column("ประเภท", "type", type.stringType()),
                col.column("พิกัดที่ตั้ง (Latitude/Longitude)", "location", type.stringType()),
                col.column("สถานะศูนย์ฯ", "status", type.stringType()),
                col.column("ข้อมูล/รายละเอียด", "detail", type.stringType()),
                col.column("ที่อยู่", "address", type.stringType()),
                col.column("สถานะข้อมูล", "complete", type.stringType()))
                .setDataSource(sample)
                .setTextStyle(fontName)
                .setColumnTitleStyle(columnTitleStyle)
                .highlightDetailEvenRows()
                .title(cmp.text("ศูนย์เรียนรู้ตามแนวพระราชดำริฯ").setStyle(header)).setPageFormat(PageType.A4, PageOrientation.LANDSCAPE)
    }

    private fun getReport3(sample: MutableList<StudyCenter>): JasperReportBuilder {
        val fontName = stl.style().setFontName(SARABUN).setFontSize(12)
        val header = stl.style().setFontName(SARABUN).bold().setFontSize(18)
        val center = stl.style().setHorizontalTextAlignment(HorizontalTextAlignment.CENTER)

        val boldStyle = stl.style(fontName).bold()
        val boldCenteredStyle = stl.style(boldStyle)
                .setHorizontalTextAlignment(HorizontalTextAlignment.CENTER)
        val columnTitleStyle = stl.style(boldCenteredStyle)
                .setBorder(stl.pen1Point())
                .setBackgroundColor(Color.LIGHT_GRAY)
        sample.forEachIndexed { index: Int, studyCenter: StudyCenter -> studyCenter.index = index + 1 }
        return DynamicReports.report().columns(//add columns
                //             title,     field name     data type
                col.column("ลำดับ", "index", type.integerType()).setStyle(center),
                col.column("ชื่อคน/ชื่อชุมชน/ชื่อกลุ่ม", "name", type.stringType()),
                col.column("ด้าน/ประเภท/ชนิด", "type", type.stringType()),
                col.column("รางวัล", "award", type.stringType()),
                col.column("ปีที่จัดตั้ง", "year", type.integerType()),
                col.column("รหัสศูนย์ต้นสังกัด", "externalId", type.stringType()),
                col.column("พิกัดที่ตั้ง (Latitude/Longitude)", "location", type.stringType()),
                col.column("ที่อยู่", "address", type.stringType()),
                col.column("สถานะศูนย์ฯ", "status", type.stringType()),
                col.column("ข้อมูลด้านศูนย์เรียนรู้อื่นๆ/รายละเอียด", "detail", type.stringType()),
                col.column("หน่วยงาน", "organization", type.stringType()),
                col.column("ผู้ประสานงาน", "coordinator", type.stringType()),
                col.column("ผู้รับผิดชอบ", "contact", type.stringType()),
                col.column("สถานะข้อมูล", "complete", type.stringType()))
                .columnGrid(ListType.HORIZONTAL_FLOW)
                .setDataSource(sample)
                .setTextStyle(fontName)
                .setColumnTitleStyle(columnTitleStyle)
                .highlightDetailEvenRows()
                .title(cmp.text("ศูนย์เรียนรู้อื่นๆ").setStyle(header)).setPageFormat(PageType.A4, PageOrientation.LANDSCAPE)
    }

    private fun getReport3Single(sample: MutableList<StudyCenter>): JasperReportBuilder {
        val fontName = stl.style().setFontName(SARABUN).setFontSize(12)
        val header = stl.style().setFontName(SARABUN).bold().setFontSize(18)
        val center = stl.style().setHorizontalTextAlignment(HorizontalTextAlignment.CENTER)

        val boldStyle = stl.style(fontName).bold()
        val boldCenteredStyle = stl.style(boldStyle)
                .setHorizontalTextAlignment(HorizontalTextAlignment.CENTER)
        val columnTitleStyle = stl.style(boldCenteredStyle)
                .setBorder(stl.pen1Point())
                .setBackgroundColor(Color.LIGHT_GRAY)
        sample.forEachIndexed { index: Int, studyCenter: StudyCenter -> studyCenter.index = index + 1 }
        return DynamicReports.report().columns(//add columns
                //             title,     field name     data type
                col.column("ลำดับ", "index", type.integerType()).setStyle(center),
                col.column("ชื่อคน/ชื่อชุมชน/ชื่อกลุ่ม", "name", type.stringType()),
                col.column("ด้าน/ประเภท/ชนิด", "type", type.stringType()),
                col.column("รางวัล", "award", type.stringType()),
                col.column("ปีที่จัดตั้ง", "year", type.integerType()),
                col.column("รหัสศูนย์ต้นสังกัด", "externalId", type.stringType()),
                col.column("พิกัดที่ตั้ง (Latitude/Longitude)", "location", type.stringType()),
                col.column("ที่อยู่", "address", type.stringType()),
                col.column("สถานะศูนย์ฯ", "status", type.stringType()),
                col.column("ข้อมูลด้านศูนย์เรียนรู้อื่นๆ/รายละเอียด", "detail", type.stringType()),
                col.column("หน่วยงาน", "organization", type.stringType()),
                col.column("ผู้ประสานงาน", "coordinator", type.stringType()),
                col.column("ผู้รับผิดชอบ", "contact", type.stringType()),
                col.column("สถานะข้อมูล", "complete", type.stringType()))
                .setDataSource(sample)
                .setTextStyle(fontName)
                .setColumnTitleStyle(columnTitleStyle)
                .highlightDetailEvenRows()
                .title(cmp.text("ศูนย์เรียนรู้อื่นๆ").setStyle(header)).setPageFormat(PageType.A4, PageOrientation.LANDSCAPE)
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

