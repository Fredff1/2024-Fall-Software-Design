package com.lab.html_editor.service;

import java.io.IOException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.lab.html_editor.service.spellcheck.SpellCheckService;

public class SpellCheckServiceTest {
    private SpellCheckService spellCheckService;
    @Before
    public void setUp(){
        spellCheckService=new SpellCheckService();
    }

    @Test
    public void testSpellCheck(){
        List<String> result;
        try{
            result=spellCheckService.checkSpelling("dshiuefefhuiheif");
            assert(result.size()>0);
        }catch(IOException e){

        }
    }
}
