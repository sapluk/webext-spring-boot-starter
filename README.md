# webext-spring-boot-starter

## 1. 项目简述：spring-boot-web扩展

本项目的目的用于扩展spring-boot-web项目，提供一些常用的功能，完全不影响原spring-boot-web自带的功能，同时无需任何配置。

## 2. 适用范围：spring-boot-web 2.x

 该项目不支持 ``spring-boot 1.x``。

## 3. 功能介绍：在spring-boot-web的基础上，支持以下特性：

1. @JsonParam 自动解析json字符串为参数
2. 支持返回对象进行下载
3. 自动解析日期，将String转换成Date和LocalDateTime
4. 输出Date类型时，直接按字面值输出格式yyyy-MM-dd HH:mm:ss

## 4. 快速使用

1. 从start.spring.io网站下载项目模板，或者IDE中新建一个spring boot web项目

2. 在您的spring boot项目的pom.xml文件引用：
```
  <dependency>
    <groupId>com.pugwoo</groupId>
    <artifactId>webext-spring-boot-starter</artifactId>
    <version>0.0.6</version>
  </dependency>
```

3. 使用者无需添加任何配置，开箱即用

# ====== 使用说明 ======

## 1. [v0.0.1] @JsonParam 自动解析json字符串为参数

  @JsonParam 用于接收前端的json字符串，并自动转换为Java Bean对象

#### · 代码示例: 
```
    // controller
    @ResponseBody @RequestMapping("/testJsonParam")
    public WebJsonBean testJsonParam(@JsonParam("test") UserDO userDO){
        //TODO something
        return new WebJsonBean();
    }
    
    // 调用接口，@JsonParam 会接收参数key=test的值，如果值为json字符串格式，则将其转换为java bean对象，并将其注入到userDO中。 
```
#### · 请求示例: 
```
请求时需要进行url编码访问
    ./testJsonParam?test=%7b"name"%3a"abc"%2c"password"%3a123456%2c"age"%3a16%7d

未编码格式: 
    ./testJsonParam?test={"name":"abc","password":123456,"age":16}
```
#### · 响应示例: 
```
{
  "code": 0,
  "msg": "成功",
  "data": {
    "name": "abc",
    "password": "123456",
    "age": 16
  }
}
```

## 2. [v0.0.1] 支持返回对象进行下载

返回DownloadBean和StreamDownloadBean进行下载。

通过 ``@ResponseBody`` 提供ajax接口，如果返回值是byte[]则直接输出二进制，如果返回值是字符串，则原样输出，如果返回值是``DownloadBean``或``StreamDownloadBean``
则下载文件（特别说明，这种方式只支持GET方式下载文件，不支持POST方式下载文件），其它均由jackson序列化为json文本(日期输出格式默认为yyyy-MM-dd HH:mm:ss)

#### · 代码示例: 
```
    // controller
    @ResponseBody @RequestMapping("/testDownloadBean")
    public DownloadBean testDownloadBean() {
        return new DownloadBean("HelloWorld.txt", "hello world你好");
    }
```
#### · 请求示例: 
```
./testDownloadBean
```
#### · 响应示例: 
```
将下载一个名为 HelloWorld.txt 的文件
```

## 3. [v0.0.1] 自动解析日期

支持自动将String类型解析为Date类型，支持多种格式。该方式同时支持@RequestParameter的注入和@JsonParam方式的注入，自动适应多种常用格式，例如``yyyy-MM-dd 
HH:mm:ss``等标准格式，具体格式如下：

```
yyyyMM                例如: 201703
yyyyMMdd              例如: 20170306
yyyyMMddHHmmss        例如: 20170306152356
yyyyMMdd HHmmss       例如: 20170306 152356
yyyy-MM               例如: 2017-03
yyyy/MM               例如: 2017/03
yyyy-MM-dd            例如: 2017-03-06
yyyy/MM/dd            例如: 2017/03/06
HH:mm:ss              例如: 16:34:32
HH:mm                 例如: 16:34
yyyy年MM月dd日         例如2017年3月30日
yyyy-MM-dd HH:mm      例如: 2017-03-06 15:23
yyyy/MM/dd HH:mm      例如: 2017/03/06 15:23
yyyy-MM-dd HH:mm:ss   例如: 2017-03-06 15:23:56
yyyy/MM/dd HH:mm:ss   例如: 2017/03/06 15:23:56
yyyy-MM-dd HH:mm:ss.SSS     例如: 2017-10-18 16:00:00.000
yyyy-MM-dd'T'HH:mm:ss.SSSX  例如: 2017-10-18T16:00:00.000Z
yyyy-MM-dd'T'HH:mm:ss.SSS   例如: 2017-10-18T16:00:00.000
```

#### · 代码示例: 
```
    // controller
    @ResponseBody @RequestMapping("/testStringToData")
    public WebJsonBean testStringToData(Date time){
        if (time != null) {
            return new WebJsonBean(ErrorCode.SUCCESS, time);
        }
        return new WebJsonBean(ErrorCode.MISSING_PARAMETERS, "time参数未提供或者格式错误，请确保time参数正确提供，如 ?time=20180808");
    }
```
#### · 请求示例: 
```
./testStringToData?time=20180808080808
```
#### · 响应示例: 
```
{
  "code": 0,
  "msg": "成功",
  "data": {
    "date": "2018-08-08 08:08:08",
    "localDateTime": "2018-07-13T11:26:54.374",
    "localTime": "11:26:54.374",
    "now": "2018-07-13 11:26:54",
    "localDate": "2018-07-13"
  }
}
```


## 4. [v0.0.1] 输出Date类型时，按0时区处理，输出格式yyyy-MM-dd HH:mm:ss

在现存项目中，Date日期的使用绝大多数情况下并没有涉及到时区，也即默认Date是一个没有时区的值，实际上Date是有时区的，默认创建的时间为0时区CST。该值存入到MySQL的datetime数据中时，也是0时区CST的值。

所以，项目中实际把Date用作一个没有时区的日期时间数据结构。在Java8中，有语义更为明确的LocalDate、LocalTime及LocalDateTime，它们并没有包含时区。

结论：在输出Date类型时，SpringBoot默认会转换成当前时区的，这和实际大多数用法不符，因此配置为直接输出为0时区的值。Date的输出格式为yyyy-MM-dd HH:mm:ss

#### · 代码示例: 
```
    // controller
    @ResponseBody @RequestMapping("/testDataToString")
    public WebJsonBean testDataToString(Date time){
        Map<String, Object> result = new HashMap<>();
        if (time == null) {
            result.put("date", "time参数未提供或者格式错误，请确保time参数正确提供，如 ?time=20180808");
        } else {
            result.put("date", time);
        }
        result.put("now", new Date());
        result.put("localDate", LocalDate.now());
        result.put("localDateTime", LocalDateTime.now());
        result.put("localTime", LocalTime.now());
        return new WebJsonBean(result);
    }
```
#### · 请求示例: 
```
./testDataToString?time=20180808
```
#### · 响应示例: 
```
{
  "code": 0,
  "msg": "成功",
  "data": {
    "date": "2018-08-08 00:00:00",
    "localDateTime": "2018-07-13T11:23:26.107",
    "localTime": "11:23:26.107",
    "now": "2018-07-13 11:23:26",
    "localDate": "2018-07-13"
  }
}
```

说明：在没有配置时区时，上面接口SpringBoot默认输出为(data部分)，注意到Date以0时区的时间来输出当前北京时区的时间：
```
{
  "date": "2018-08-07T16:00:00.000+0000",
  "localDateTime": "2018-07-13T11:23:26.107",
  "localTime": "11:23:26.107",
  "now": "2018-07-13T03:23:26.107+0000",
  "localDate": "2018-07-13"
}
```

