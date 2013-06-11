//
//  GGPoliceDetailViewController.h
//  policeOnline
//
//  Created by towne on 13-4-30.
//  Copyright (c) 2013年 tmd. All rights reserved.
//

#import "GGBaseViewController.h"
#import "GGPoliceman.h"
#import "GGUtils.h"

@interface GGPoliceDetailViewController : GGBaseViewController
@property (copy) NSString *naviTitleString;
@property (strong,nonatomic)GGPoliceman * policeman;
@property (nonatomic)BOOL keep;   // 收藏 1 or 取消收藏 0

-(IBAction)getMyfavorite:(id)sender;
-(IBAction)CALL:(id)sender;
@end
