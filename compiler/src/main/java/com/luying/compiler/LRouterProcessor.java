package com.luying.compiler;

import com.google.auto.service.AutoService;

import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedOptions;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types

/**
 * Create by luying
 * 2020-03-23
 * 编译器处理注解
 */

@AutoService(Processor.class)   //编译期绑定注解
@SupportedAnnotationTypes({"com.luying.annotation.LRouter"})    //监听注解
@SupportedSourceVersion(SourceVersion.RELEASE_7)      //版本
@SupportedOptions("myvalue")    //接受外部传值
public class LRouterProcessor extends AbstractProcessor {

    private Elements elementTool; //操作Element的工具类，Element对象内部包含类的所有信息(跟插件化热修复的Element不同)

    private Types typeTool;      //类信息的工具类

    private Messager messager;  //打印日志

    private Filer filer;    //文件生成器


    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        elementTool = processingEnvironment.getElementUtils();
        typeTool = processingEnvironment.getTypeUtils();
        messager = processingEnvironment.getMessager();
        filer = processingEnvironment.getFiler();

    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        return false;
    }
}
