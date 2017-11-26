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

package com.example.java.gettingstarted;


import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.base.DRReport;
import net.sf.dynamicreports.report.builder.DynamicReports;
import net.sf.dynamicreports.report.constant.PageOrientation;
import net.sf.dynamicreports.report.constant.PageType;
import net.sf.dynamicreports.report.exception.DRException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.swing.table.DefaultTableModel;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;

import static net.sf.dynamicreports.report.builder.DynamicReports.*;

@SpringBootApplication
@RestController
public class HelloworldApplication {
    @RequestMapping("/")
    public String home() {
        return "Hello World!";
    }

    DefaultTableModel tableModel;

    private void TableModelData() {
        String[] columnNames = {"Id", "Name", "Department", "Email"};
        String[][] data = {
                {"111", "G Conger", " Orthopaedic", "jim@wheremail.com"},
                {"222", "A Date", "ENT", "adate@somemail.com"},
                {"333", "R Linz", "Paedriatics", "rlinz@heremail.com"},
                {"444", "V Sethi", "Nephrology", "vsethi@whomail.com"},
                {"555", "K Rao", "Orthopaedics", "krao@whatmail.com"},
                {"666", "V Santana", "Nephrology", "vsan@whenmail.com"},
                {"777", "J Pollock", "Nephrology", "jpol@domail.com"},
                {"888", "H David", "Nephrology", "hdavid@donemail.com"},
                {"999", "P Patel", "Nephrology", "ppatel@gomail.com"},
                {"101", "C Comer", "Nephrology", "ccomer@whymail.com"}
        };
        tableModel = new DefaultTableModel(data, columnNames);
    }

    @RequestMapping("/report1")
    public void report(HttpServletResponse httpServletResponse) {
        try {
            ArrayList<Sample> sample = new ArrayList<Sample>();
            sample.add(new Sample("shoe",1, new BigDecimal(100)));
            JasperReportBuilder build = DynamicReports.report().columns(//add columns
                    //             title,     field name     data type
                    col.column("Item", "item", type.stringType()),
                    col.column("Quantity", "quantity", type.integerType()),
                    col.column("Unit price", "unitprice", type.bigDecimalType()))
                    .setDataSource(sample)
                    .title(cmp.text("Getting started")).setPageFormat(PageType.A4, PageOrientation.LANDSCAPE)
                    .pageFooter(cmp.pageXofY()).toHtml(httpServletResponse.getOutputStream());
        } catch (DRException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    /**
     * (Optional) App Engine health check endpoint mapping.
     *
     * @see <a href="https://cloud.google.com/appengine/docs/flexible/java/how-instances-are-managed#health_checking"></a>
     * If your app does not handle health checks, a HTTP 404 response is interpreted
     * as a successful reply.
     */
    @RequestMapping("/_ah/health")
    public String healthy() {
        // Message body required though ignored
        return "Still surviving.";
    }

    public static void main(String[] args) {
        SpringApplication.run(HelloworldApplication.class, args);
    }
}
