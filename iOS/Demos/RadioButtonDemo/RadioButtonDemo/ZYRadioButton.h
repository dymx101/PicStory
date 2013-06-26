//
//  ZYRadioButton.h
//  RadioButtonDemo
//
//  Created by zhangyuc on 13-5-22.
//  Copyright (c) 2013年 zhangyuc. All rights reserved.
//

#import <UIKit/UIKit.h>


@protocol RadioButtonDelegate <NSObject>

-(void)radioButtonSelectedAtIndex:(NSUInteger)index inGroup:(NSString*)groupId;
@end

@interface ZYRadioButton : UIView{
    NSString *_groupId;
    NSUInteger _index;
    UIButton *_button;
}
//GroupId
@property(nonatomic,retain)NSString *groupId;
//Group的索引
@property(nonatomic,assign)NSUInteger index;

//初始化RadioButton控件
-(id)initWithGroupId:(NSString*)groupId index:(NSUInteger)index;
//为
+(void)addObserverForGroupId:(NSString*)groupId observer:(id)observer;

@end
