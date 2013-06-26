//
//  ZYRadioButton.m
//  RadioButtonDemo
//
//  Created by zhangyuc on 13-5-22.
//  Copyright (c) 2013年 zhangyuc. All rights reserved.
//

#import "ZYRadioButton.h"

@interface ZYRadioButton()
-(void)defaultInit;
-(void)otherButtonSelected:(id)sender;
-(void)handleButtonTap:(id)sender;
@end

@implementation ZYRadioButton

@synthesize groupId=_groupId;
@synthesize index=_index;


static const NSUInteger kRadioButtonWidth=22;
static const NSUInteger kRadioButtonHeight=22;

static NSMutableArray *rb_instances=nil;
static NSMutableDictionary *rb_observers=nil;

- (id)initWithFrame:(CGRect)frame
{
    self = [super initWithFrame:frame];
    if (self) {
        // Initialization code
    }
    return self;
}



#pragma mark - Object Lifecycle

-(id)initWithGroupId:(NSString*)groupId index:(NSUInteger)index{
    //调用初始化函数
    self = [self init];
    if (self) {
        _groupId = groupId;
        _index = index;
    }
    return  self;
}

- (id)init{
    self = [super init];
    if (self) {
        [self defaultInit];
    }
    return self;
}

#pragma mark - RadioButton init
//初始化按钮
-(void)defaultInit{
    //设置控件自己的Frame
    self.frame = CGRectMake(0, 0, kRadioButtonWidth, kRadioButtonHeight);
    
    // 客户定制按钮
    _button = [UIButton buttonWithType:UIButtonTypeCustom];
    _button.frame = CGRectMake(0, 0,kRadioButtonWidth, kRadioButtonHeight);
    //一个布尔值,决定了图像的变化当按钮高亮显示。
    _button.adjustsImageWhenHighlighted = NO;
    
    //正常状态下的按钮背景图片
    [_button setImage:[UIImage imageNamed:@"RadioButton-Unselected"] forState:UIControlStateNormal];
    //选择状态下的按钮背景图片
    [_button setImage:[UIImage imageNamed:@"RadioButton-Selected"] forState:UIControlStateSelected];
    //添加按钮监听事件
    [_button addTarget:self action:@selector(handleButtonTap:) forControlEvents:UIControlEventTouchUpInside];
    //添加按钮
    [self addSubview:_button];
    
    //注册观察者实例，就是向数组中添加观察者对象
    [ZYRadioButton registerInstance:self];
}
#pragma mark - Manage Instances
//添加选中的RadioButton
+(void)registerInstance:(ZYRadioButton*)radioButton{
    if(!rb_instances){
        rb_instances = [[NSMutableArray alloc] init];
    }
    
    [rb_instances addObject:radioButton];
    //弱引用
    [radioButton release];
}

#pragma mark - Tap handling
//按钮监听事件
-(void)handleButtonTap:(id)sender{
    //对应的按钮设置为选中状态
    [_button setSelected:YES];
    [ZYRadioButton buttonSelected:self];
}

#pragma mark - Class level handler
//RadioButton选中后调用的事件
+(void)buttonSelected:(ZYRadioButton*)radioButton{
    // 通知观察者
    if (rb_observers) {
        //获得观察者对象
        id observer= [rb_observers objectForKey:radioButton.groupId];
        
        //判断该类中是否存在radioButtonSelectedAtIndex:inGroup: 这个方法
        if(observer && [observer respondsToSelector:@selector(radioButtonSelectedAtIndex:inGroup:)]){
            //调用观察者的代理的方法
            [observer radioButtonSelectedAtIndex:radioButton.index inGroup:radioButton.groupId];
        }
    }
    
    // 设置其他按钮状态为未选中
    if (rb_instances) {
        for (int i = 0; i < [rb_instances count]; i++) {
            ZYRadioButton *button = [rb_instances objectAtIndex:i];
            if (![button isEqual:radioButton]) {
                [button otherButtonSelected:radioButton];
            }
        }
    }
}
//未选中状态按钮设置为未选中
-(void)otherButtonSelected:(id)sender{
    // Called when other radio button instance got selected
    if(_button.selected){
        [_button setSelected:NO];
    }
}

#pragma mark - Observer

//添加观察者
+(void)addObserverForGroupId:(NSString*)groupId observer:(id)observer{
    if(!rb_observers){
        rb_observers = [[NSMutableDictionary alloc] init];
    }
    
    if ([groupId length] > 0 && observer) {
        [rb_observers setObject:observer forKey:groupId];
        // Make it weak reference
        [observer release];
    }
}

@end
