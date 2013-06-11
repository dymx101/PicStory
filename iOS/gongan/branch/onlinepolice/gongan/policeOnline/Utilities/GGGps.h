//
//  GGGps.h
//  policeOnline
//
//  Created by dong yiming on 13-4-28.
//  Copyright (c) 2013年 tmd. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <CoreLocation/CoreLocation.h>

@class GGGps;

@protocol GGGpsDelegate <NSObject>

-(void)gps:(GGGps *)aGPS gotLongitude:(float)aLongitude latitude:(float)aLatitude;

@end

@interface GGGps : NSObject <CLLocationManagerDelegate>
AS_SINGLETON(GGGps)
@property (weak)    id<GGGpsDelegate>   delegate;
@property (strong)  CLLocation          *location;

-(void)startUpdate;
-(void)stopUpdate;
@end
