package com.luying.compiler;

import com.google.auto.service.AutoService;
import com.luying.annotation.LRouter;

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
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;

/**
 * Create by luying
 * 2020-03-23
 * 编译器处理注解
 */

@AutoService(Processor.class)   //编译期绑定注解
@SupportedAnnotationTypes({"com.luying.annotation.LRouter"})    //监听注解
@SupportedSourceVersion(SourceVersion.RELEASE_7)      //版本
@SupportedOptions("mValue")    //接受外部build.gradle下面定义的值,用来编译器 java和android进行传值
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


        String mValue = processingEnvironment.getOptions().get("mValue");//在app的build.gradle下面定义了一个options
        messager.printMessage(Diagnostic.Kind.NOTE, "-----》》》》" + mValue);

    }


    /**
     * 每扫描到一个LRouter内部定义的注解就调用一次这个方法
     * @param set
     * @param roundEnvironment
     * @return true:表示注解处理完成,后续如果没有变动service不会再处理  false还没有处理
     */
    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        if (set.isEmpty()){//没有扫描到注解
            return false;//表示没处理
        }
        //获取包名
        //获取类名

        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(LRouter.class);

        for (Element element : elements){
            String pkgName = elementTool.getPackageOf(element).getQualifiedName().toString();
            String clazzName = element.getSimpleName().toString();
            messager.printMessage(Diagnostic.Kind.NOTE, "被LRouter注解的类有" + pkgName +"."+ clazzName);
        }



        return false;
    }
}
