package com.example.openoffice.controller;

import com.github.openoffice.OpenOffice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OpenOfficeController {
    @Autowired
    private OpenOffice openOffice;

    @GetMapping("/change")
    public void change(){
        openOffice.officeToPdf("E:\\国土空间规划一张图系统\\规划一张图开发计划修订20204020.xlsx");
    }
}
