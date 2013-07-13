//
//  ViewController.m
//  TestScoket
//
//  Created by MagicStudio on 13-5-6.
//  Copyright (c) 2013年 MagicStudio. All rights reserved.
//

#import "ViewController.h"


#include <sys/types.h>
#include <sys/socket.h>
#include <string.h>
#include <netinet/in.h>
#include <stdio.h>
#include <stdlib.h>
#include <arpa/inet.h>
#include <unistd.h>
#include <netdb.h>



@interface ViewController (){
    
}

@end

@implementation ViewController

//在此处更改相对应的服务器ip
//char host[20] = "192.168.1.110";
//int tcpPort = 4321;
//int udpPort = 1234;
char host[20] = "127.0.0.1";
int tcpPort = 12211;
int udpPort = 0;

int sd;
struct sockaddr_in pin;
struct hostent *nlp_host;


- (void)viewDidLoad
{
    [super viewDidLoad];
	// Do any additional setup after loading the view, typically from a nib.
}

- (void)viewDidUnload
{
    [super viewDidUnload];
    // Release any retained subviews of the main view.
    
    //初始化主机名和端口。主机名可以是IP，也可以是可被解析的名称
    
    
    //解析域名，如果是IP则不用解析，如果出错，显示错误信息
    while ((nlp_host=gethostbyname(host))==0){
        printf("Resolve Error!\n");
    }
    
    
    //设置pin变量，包括协议、地址、端口等，此段可直接复制到自己的程序中
    bzero(&pin,sizeof(pin));
    pin.sin_family=AF_INET;                 //AF_INET表示使用IPv4
    pin.sin_addr.s_addr=htonl(INADDR_ANY);
    pin.sin_addr.s_addr=((struct in_addr *)(nlp_host->h_addr))->s_addr;
    pin.sin_port=htons(tcpPort);
    
    //建立socket
    sd=socket(AF_INET,SOCK_STREAM,0);
    
    //建立连接
    while (connect(sd,(struct sockaddr*)&pin,sizeof(pin))==-1){
        printf("Connect Error!\n");
    }
}

- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation
{
    return (interfaceOrientation != UIInterfaceOrientationPortraitUpsideDown);
}

- (IBAction)sendTCPString {
    
    //发送数据
    char line[1000] = "Sending string:Client to Server!你好";
    char recvBuf[1000];
    
    sendto(sd, line, strlen(line)+1, 0,(struct sockaddr *)&pin, sizeof(pin));
    
    printf("send string success\n");
    
    //接收数据
    recv(sd, recvBuf, sizeof(recvBuf), 0);
    //    recvfrom(fd,recvBuf,128,0,(struct sockaddr *)&address, (socklen_t*)sizeof(address));
    //    char* ipSvr = inet_ntoa(fd.sin_addr);
    NSString *str1 = [NSString stringWithUTF8String:recvBuf];
    NSLog(@"===== : %@",str1);
    printf("准备输出服务器反馈信息");
    printf("服务器 said: %s\n",recvBuf);
    
}

- (IBAction)sendUDPString {
    static int i = 0;
    
    int fd;
    
    struct sockaddr_in address;
    
    int address_len;
    
    char line[1000] = "Sending string:Client to Server!你好";
    char recvBuf[1000];
    int n;
    
    
    
    //建立套接口
    
    fd = socket(AF_INET, SOCK_DGRAM, 0);//AF_INET和SOCK_DGRAM的组合对应UDP协议
    
    
    
    //联接
    
    bzero(&address, sizeof(address));
    
    address.sin_family = AF_INET;
    
    address.sin_addr.s_addr = inet_addr(host);
    
    address.sin_port = htons(udpPort);
    
    address_len = sizeof(address);
    
    
    
    //发送数据
    
    sendto(fd, line, strlen(line)+1, 0,(struct sockaddr *)&address, sizeof(address));
    
    printf("send string success\n");
    
    //接收数据
    recv(fd, recvBuf, sizeof(recvBuf), 0);
    //    recvfrom(fd,recvBuf,128,0,(struct sockaddr *)&address, (socklen_t*)sizeof(address));
    //    char* ipSvr = inet_ntoa(fd.sin_addr);
    NSString *str1 = [NSString stringWithUTF8String:recvBuf];
    NSLog(@"===== : %@",str1);
    printf("准备输出服务器反馈信息");
    printf("服务器 said: %s\n",recvBuf);
}


@end
