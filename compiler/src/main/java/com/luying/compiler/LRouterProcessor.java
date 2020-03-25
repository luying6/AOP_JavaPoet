package com.luying.compiler;

import com.google.auto.service.AutoService;
import com.luying.annotation.LRouter;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
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
import javax.lang.model.element.Modifier;
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

            LRouter lRouter = element.getAnnotation(LRouter.class);

            //使用javaPoet
            //1.生成方法
            //2.生成类
            //3.生成包
            //例1
//        MethodSpec mainMethod = MethodSpec.methodBuilder("main")
//                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
//                .returns(void.class)
//                .addParameter(String[].class, "args")
//                .addStatement("$T.out.println($S)", System.class, "Hello JavaPoet!")
//                .build();
//
//        TypeSpec testClass = TypeSpec.classBuilder("HelloWorld")
//                .addMethod(mainMethod)
//                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
//                .build();
//
//
//        JavaFile pkgInfo = JavaFile.builder("com.luying.aop_javapoet", testClass)
//                .build();
//        try {
//            pkgInfo.writeTo(filer);
//            messager.printMessage(Diagnostic.Kind.NOTE, "HelloWorlds生成成功");
//
//        } catch (IOException e) {
//            e.printStackTrace();
//            messager.printMessage(Diagnostic.Kind.NOTE, "HelloWorlds生成失败");
//        }
//




            //例2

//        public class MainActivity$$$$$$ARouter {
//            public static Class findTargetClass(String path){
//                return path.equals("app MainActivity")? MainActivity.class : null
//            }
//        }



            MethodSpec findTargetClass = MethodSpec.methodBuilder("findTargetClass")
                    .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                    .returns(Class.class)
                    .addParameter(String.class, "path")
                    .addStatement("return path.equals($S) ? $T.class : null",lRouter.path(), ClassName.get((TypeElement) element))
                    .build();



            String findClassName = clazzName + "$$$$$$ARouter";


            TypeSpec myClass = TypeSpec.classBuilder(findClassName)
                    .addModifiers(Modifier.PUBLIC)
                    .addMethod(findTargetClass)
                    .build();

            JavaFile packf = JavaFile.builder(pkgName, myClass).build();

            try {
                packf.writeTo(filer);
            } catch (IOException e) {
                e.printStackTrace();
                messager.printMessage(Diagnostic.Kind.NOTE, findClassName+"构建失败");
            }
        }





        return false;
    }
}
