//
//  GGRadioButton.h
//  policeOnline
//
//  Created by towne on 13-6-26.
//  Copyright (c) 2013年 tmd. All rights reserved.
//

#import <UIKit/UIKit.h>

@protocol RadioButtonDelegate <NSObject>

-(void)radioButtonSelectedAtIndex:(NSUInteger)index inGroup:(NSString*)groupId;
@end

@interface GGRadioButton : UIView
{
    NSString *_groupId;
    NSUInteger _index;
    UIButton *_button;
}

//GroupId
@property(nonatomic,strong)NSString *groupId;
//Group的索引
@property(nonatomic,assign)NSUInteger index;

//初始化RadioButton控件
-(id)initWithGroupId:(NSString*)groupId index:(NSUInteger)index;
//为
+(void)addObserverForGroupId:(NSString*)groupId observer:(id)observer;
@end
