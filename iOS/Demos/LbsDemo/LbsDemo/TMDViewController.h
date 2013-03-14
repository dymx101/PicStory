//
//  TMDViewController.h
//  LbsDemo
//
//  Created by Yim Daniel on 13-2-26.
//  Copyright (c) 2013年 Yim Daniel. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface TMDViewController : UIViewController
<MKMapViewDelegate, CLLocationManagerDelegate>

@property (nonatomic, strong) CLLocationManager *myLocationManager;
@property (nonatomic, strong) CLGeocoder        *myGeocoder; // if ios version < 5, use MKReverseGeocoder instead.
@end
