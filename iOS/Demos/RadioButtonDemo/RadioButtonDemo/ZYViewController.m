//
//  ZYViewController.m
//  RadioButtonDemo
//
//  Created by zhangyuc on 13-5-22.
//  Copyright (c) 2013年 zhangyuc. All rights reserved.
//

#import "ZYViewController.h"

@interface ZYViewController ()

@end

@implementation ZYViewController

- (void)viewDidLoad
{
    [super viewDidLoad];
    //初始化视图容器
    UIView *container = [[UIView alloc] initWithFrame:CGRectMake(20, 20, 280, 400)];
    container.backgroundColor = [UIColor lightGrayColor];
    [self.view addSubview:container]
    ;
    //初始化UILabel并添加到之前的视图容器
    UILabel *questionText = [[UILabel alloc] initWithFrame:CGRectMake(0,0,280,20)];
    questionText.backgroundColor = [UIColor clearColor];
    questionText.text = @"Which color do you like?";
    [container addSubview:questionText];
    //初始化单选按钮控件
    ZYRadioButton *rb1 = [[ZYRadioButton alloc] initWithGroupId:@"first group" index:0];
    ZYRadioButton *rb2 = [[ZYRadioButton alloc] initWithGroupId:@"first group" index:1];
    ZYRadioButton *rb3 = [[ZYRadioButton alloc] initWithGroupId:@"first group" index:2];
    //设置Frame
    rb1.frame = CGRectMake(10,30,22,22);
    rb2.frame = CGRectMake(10,60,22,22);
    rb3.frame = CGRectMake(10,90,22,22);
    //添加到视图容器
    [container addSubview:rb1];
    [container addSubview:rb2];
    [container addSubview:rb3];
    
    [rb1 release];
    [rb2 release];
    [rb3 release];
    //初始化第一个单选按钮的UILabel
    UILabel *label1 =[[UILabel alloc] initWithFrame:CGRectMake(40, 30, 60, 20)];
    label1.backgroundColor = [UIColor clearColor];
    label1.text = @"Red";
    [container addSubview:label1];
    [label1 release];
    
    UILabel *label2 =[[UILabel alloc] initWithFrame:CGRectMake(40, 60, 60, 20)];
    label2.backgroundColor = [UIColor clearColor];
    label2.text = @"Green";
    [container addSubview:label2];
    [label2 release];
    
    UILabel *label3 =[[UILabel alloc] initWithFrame:CGRectMake(40, 90, 60, 20)];
    label3.backgroundColor = [UIColor clearColor];
    label3.text = @"Blue";
    [container addSubview:label3];
    [label3 release];
    
    //按照GroupId添加观察者
    [ZYRadioButton addObserverForGroupId:@"first group" observer:self];
    
    [container release];
    [super viewDidLoad];
}



//代理方法
-(void)radioButtonSelectedAtIndex:(NSUInteger)index inGroup:(NSString *)groupId{
    NSLog(@"changed to %d in %@",index,groupId);
}
- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

@end
