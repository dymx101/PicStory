//
//  GGSocialProfile.h
//  WeiGongAn
//
//  Created by dong yiming on 13-4-2.
//  Copyright (c) 2013年 WeiGongAn. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "GGDataModel.h"

@interface GGWantedCategory : GGDataModel
@property (copy) NSString   *name;
@property (assign) long long superID;
@property (copy) NSString *addTime;
@end
