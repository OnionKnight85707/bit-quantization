package com.mzwise;

import cn.hutool.core.util.StrUtil;
import cn.hutool.setting.dialect.Props;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.builder.ConfigBuilder;
import com.baomidou.mybatisplus.generator.config.po.LikeTable;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.FileType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

/**
 * MyBatisPlus代码生成器
 * Created by admin on 2020/8/20.
 */
public class MyBatisPlusGenerator {

    public static void main(String[] args) {
        String projectPath = System.getProperty("user.dir");
        String echoMicroName = scanner("服务名, 以>号隔开");
        String microName;
        String coreName;
        if (echoMicroName.contains(">")) {
            String[] micros = echoMicroName.split(">");
            microName = micros[0];
            coreName = micros[1];
        } else {
            microName = coreName = echoMicroName;
        }
        String microPath = projectPath + "/" + microName; // 当前服务
        String corePath = projectPath + "/" + coreName; // 核心服务
        System.out.println("微服务: "+microPath);
        System.out.println("核心依赖: " + corePath);
        String moduleName = scanner("模块名");
        String[] tableNames = scanner("表名，多个英文逗号分割").split(",");
        // 代码生成器
        AutoGenerator autoGenerator = new AutoGenerator();
        autoGenerator.setGlobalConfig(initGlobalConfig(microPath));
        autoGenerator.setDataSource(initDataSourceConfig());
        autoGenerator.setPackageInfo(initPackageConfig(microPath, corePath, moduleName));
        autoGenerator.setCfg(initInjectionConfig(corePath, moduleName));
        autoGenerator.setTemplate(initTemplateConfig());
        autoGenerator.setStrategy(initStrategyConfig(tableNames));
        autoGenerator.setTemplateEngine(new VelocityTemplateEngine());
        autoGenerator.execute();
    }

    /**
     * 读取控制台内容信息
     */
    private static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(("请输入" + tip + "："));
        if (scanner.hasNext()) {
            String next = scanner.next();
            if (StrUtil.isNotEmpty(next)) {
                return next;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }

    /**
     * 初始化全局配置
     */
    private static GlobalConfig initGlobalConfig(String projectPath) {
        GlobalConfig globalConfig = new GlobalConfig();
        globalConfig.setOutputDir(projectPath + "/src/main/java");
        globalConfig.setAuthor("admin");
        globalConfig.setOpen(false);
        globalConfig.setSwagger2(true);
        globalConfig.setBaseResultMap(true);
        globalConfig.setFileOverride(true);
        globalConfig.setDateType(DateType.ONLY_DATE);
        globalConfig.setEntityName("%s");
        globalConfig.setMapperName("%sMapper");
        globalConfig.setXmlName("%sMapper");
        globalConfig.setServiceName("%sService");
        globalConfig.setServiceImplName("%sServiceImpl");
        globalConfig.setControllerName("%sController");
        return globalConfig;
    }

    /**
     * 初始化数据源配置
     */
    private static DataSourceConfig initDataSourceConfig() {
        Props props = new Props("generator.properties");
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setUrl(props.getStr("dataSource.url"));
        dataSourceConfig.setDriverName(props.getStr("dataSource.driverName"));
        dataSourceConfig.setUsername(props.getStr("dataSource.username"));
        dataSourceConfig.setPassword(props.getStr("dataSource.password"));
        return dataSourceConfig;
    }

    /**
     * 初始化包配置
     */
    private static PackageConfig initPackageConfig(String microPath, String corePath, String moduleName) {
        Props props = new Props("generator.properties");
        PackageConfig packageConfig = new PackageConfig();
        packageConfig.setModuleName(moduleName);
        String packageBase = props.getStr("package.base");
        packageConfig.setParent(packageBase);
        packageConfig.setEntity("entity");
        HashMap<String, String> pathInfo = new HashMap<>();
        String packagePath = packageBase.replace(".", "/");
        pathInfo.put(ConstVal.CONTROLLER_PATH, microPath+"/src/main/java/"+ packagePath +"/"+moduleName+"/controller");
        pathInfo.put(ConstVal.MAPPER_PATH, corePath+"/src/main/java/"+ packagePath +"/"+moduleName+"/mapper");
        pathInfo.put(ConstVal.ENTITY_PATH, corePath+"/src/main/java/"+ packagePath +"/"+moduleName+"/entity");
        pathInfo.put(ConstVal.SERVICE_IMPL_PATH, corePath+"/src/main/java/"+ packagePath +"/"+moduleName+"/service/impl");
        pathInfo.put(ConstVal.SERVICE_PATH, corePath+"/src/main/java/"+ packagePath +"/"+moduleName+"/service");
        packageConfig.setPathInfo(pathInfo);
        return packageConfig;
    }

    /**
     * 初始化模板配置
     */
    private static TemplateConfig initTemplateConfig() {
        TemplateConfig templateConfig = new TemplateConfig();
        //可以对controller、service、entity模板进行配置
        //mapper.xml模板需单独配置
        templateConfig.setXml(null);
        return templateConfig;
    }

    /**
     * 初始化策略配置
     */
    private static StrategyConfig initStrategyConfig(String[] tableNames) {
        StrategyConfig strategyConfig = new StrategyConfig();
        strategyConfig.setNaming(NamingStrategy.underline_to_camel);
        strategyConfig.setColumnNaming(NamingStrategy.underline_to_camel);
        strategyConfig.setEntityLombokModel(true);
        strategyConfig.setRestControllerStyle(true);

        // 省略命名规范、entity模式等其他配置
        List<TableFill> tableFillList = new ArrayList<>();
        tableFillList.add(new TableFill("create_time", FieldFill.INSERT));
        tableFillList.add(new TableFill("create_date", FieldFill.INSERT));
        tableFillList.add(new TableFill("update_time", FieldFill.INSERT_UPDATE));
        strategyConfig.setTableFillList(tableFillList);

        //当表名中带*号时可以启用通配符模式
        if (tableNames.length == 1 && tableNames[0].contains("*")) {
            String[] likeStr = tableNames[0].split("_");
            String likePrefix = likeStr[0] + "_";
            strategyConfig.setLikeTable(new LikeTable(likePrefix));
        } else {
            strategyConfig.setInclude(tableNames);
        }
        return strategyConfig;
    }

    /**
     * 初始化自定义配置
     */
    private static InjectionConfig initInjectionConfig(String projectPath, String moduleName) {
        // 自定义配置
        InjectionConfig injectionConfig = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };
        injectionConfig.setFileCreate(new IFileCreate() {

            @Override
            public boolean isCreate(ConfigBuilder configBuilder, FileType fileType, String filePath) {
                // 判断自定义文件夹是否需要创建,这里调用默认的方法
                checkDir(filePath);
                //对于已存在的文件，只需重复生成 entity 和 mapper.xml
                File file = new File(filePath);
                boolean exist = file.exists();
                if (exist) {
                    return filePath.endsWith("Mapper.xml") || FileType.ENTITY == fileType;
                }
                //不存在的文件都需要创建
                return true;
            }
        });

        // 模板引擎是Velocity
        String templatePath = "/templates/mapper.xml.vm";
        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return projectPath + "/src/main/resources/mapper/" + moduleName
                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
        injectionConfig.setFileOutConfigList(focList);
        return injectionConfig;
    }


}