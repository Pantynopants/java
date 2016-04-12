#可以调整AgendaImpl.java中的文件目录来使用
    1.启动3个控制台，模拟客户端和服务端在不同的jvm上
    2.使用javac *.java 编译所有之后，使用其中一个控制台输入rmiregistry进行注册
    3.第二个控制台输入java Server来运行服务端。显示success bind即为成功
    4.第二个控制台输入java Client运行服务端，生成的文件在AgendaImpl.java中指定
    5.输入相关命令，如login b b, query b b 1 2, register a a, add a a b qwq 22 33, ...
