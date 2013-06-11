//
//  GGTicker.h
//  WeiGongAn
//
//  Created by dong yiming on 13-4-21.
//  Copyright (c) 2013年 WeiGongAn. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "GGDataModel.h"

//id
//name
//sex
//number
//stationID
//phone
//photo

@interface GGPoliceman : GGDataModel
@property (copy) NSString *name;
@property (copy) NSString *gender;
@property (copy) NSString *number;
@property (copy) NSString *phone;
@property (copy) NSString *photo;

@property (copy) NSString *stationName;
@property (copy) NSString *stationPhone;
@property (copy) NSString *superviserPhone;
@end
