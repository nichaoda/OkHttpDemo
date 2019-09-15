# OkHttpDemo
OkHttp的学习和使用  
## Url
假设有个url如下：  
http://www.abc.com:1234/defg/hijk/lmn.index?name=a&number=1#hijk  
一个完整的url包括**协议、Host、端口、资源、参数和锚**  
**协议(scheme)**——该url中为http，其它的还有https、ftp等  
**Host**——通常以**IP地址或者域名**表示，该url中为www.abc.com  
**端口**——与IP地址或者域名的**:**为分隔的内容，该url中为1234，Http的默认端口为80，使用这种情况时端口可省略。  
**资源**——从域名以后的**第一个"/"和最后一个"/"中的内容为资源存放的路径**，**最后一个"/"和"?"之间的内容是资源名称**，资源包括这两部分，该url中为/defg/hijk/lmn.index  
**参数**——从"?"到"#"之间的内容为参数部分，可以有多个参数，中间用"&"分隔，本url中为name=a&number=1  
**锚**——从"#"到最后都属于锚，锚点是在用户打开页面时滚动到该位置，本url中为hijk  
## HTTP
Http协议中通信双方称为Client和Server(或Host)，Client通过http协议发送**Request**给Server，Server收到Request后通过处理返回**Response**给Client。  
Http协议是**无状态的**，是指协议对于事务处理没有记忆能力，服务器不知道客户端的状态。  
Http keep-alive是持久连接。它的特点是只要任意一端没有提出断开TCP连接，那么就保持TCP的连接状态。**持久连接**是为了能够在建立1次TCP连接后进行多次请求和响应的交互。  
在Http/1.1中，所有的连接默认为持久连接。而因为有了持久连接，使得**请求以管线化方式发送成为可能，也就是可以同时并行发送多个请求**。  
由于Http协议**无状态**，它不对之前发生过的请求和响应的状态进行管理，因此引入了**Cookie技术**。Cookie技术通过在请求和响应报文中写入Cookie信息来控制客户端的状态。  
**Cookie**根据从服务器端发送的响应报文中的**Set-Cookie**的首部字段信息，通知客户端保存Cookie。当下次客户端再往该服务器发送请求时，客户端会自动在报文中加入Cookie值。  
服务器端发现客户端发送的Cookie值后会去检查是从哪个客户端发来的连接请求，然后对比服务器上的记录，获取到之前的状态。
### Http消息结构:Request
**Request line(请求行)**——包括http请求方法，请求URI，http版本  
**Request Header(请求头)**——http头部信息，格式为key:value的内容都是请求头  
**body(请求体)**——发送给服务器的**query**信息(使用**GET**时，body为空)  
### Http消息结构:Response
**Response line(状态行)**——http版本、状态码、原因短语  
**Response Header**——**response**头信息，格式为key:value的内容都是响应头  
**body**——返回的请求资源主体  
## 关于Android版本和网络请求
从Android P开始，访问非https链接时需要添加network_security_config.xml，步骤：  
1. 在res下新建目录xml，然后创建文件network_security_config.xml
```xml
<?xml version="1.0" encoding="utf-8"?>
<network-security-config>
    <base-config cleartextTrafficPermitted="true" />
</network-security-config>
```
2. 在AndroidManifest中的Application添加属性 **android:networkSecurityConfig="@xml/network_security_config.xml"**  
## execute和enqueue方法
okhttpclient进行newCall操作后有execute和enqueue两种方式，前者是同步操作，会进行阻塞；后者是异步操作，Android客户端一般都用后者。但是如果需要进行UI操作时，后者得通过runonuithread或者通过handle进行处理。
