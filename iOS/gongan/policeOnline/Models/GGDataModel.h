//
//  GGDataModel.h
//  WeiGongAn
//
//  Created by dong yiming on 13-4-2.
//  Copyright (c) 2013年 WeiGongAn. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface GGDataModel : NSObject
@property (assign) long long ID;

+(id)model;

-(void)parseWithData:(NSDictionary *)aData;

-(NSString *)intervalStringWithDate:(long long)aDate;
@end
