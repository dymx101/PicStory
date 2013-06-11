//
//  GGWanted.h
//  policeOnline
//
//  Created by dong yiming on 13-4-28.
//  Copyright (c) 2013年 tmd. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "GGDataModel.h"

//id
//title
//content
//addTime
//updateTime
//type
//wantedManName
//wantedManSex
//wantedManNumber
//wantedManAddress
//rewordTime

@interface GGWanted : GGDataModel
@property (copy) NSString *title;
@property (copy) NSString *content;
@property (copy) NSString *type;
@property (copy) NSString *rewardTime;

@property (copy) NSString *wantedManName;
@property (copy) NSString *wantedManGender;
@property (copy) NSString *wantedManAddress;

@property (copy) NSString *addTime;
@property (copy) NSString *updateTime;

@end
