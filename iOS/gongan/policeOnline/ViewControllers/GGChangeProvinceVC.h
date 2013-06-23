//
//  GGChangeProvinceVC.h
//  policeOnline
//
//  Created by towne on 13-6-17.
//  Copyright (c) 2013年 tmd. All rights reserved.
//

#import "GGBaseViewController.h"

@protocol GGChangeProvinceDelegate

@required

-(void)switchToArea:(NSNumber *) index;

@end


@interface GGChangeProvinceVC : GGBaseViewController<UITableViewDataSource, UITableViewDelegate>

@end
