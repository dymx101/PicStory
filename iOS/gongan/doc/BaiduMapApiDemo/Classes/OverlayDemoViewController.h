//
//  OverlayDemoViewController.h
//  BaiduMapApiDemo
//
//  Copyright 2011 Baidu Inc. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "BMapKit.h"

@interface OverlayDemoViewController : UIViewController <BMKMapViewDelegate>{
	IBOutlet BMKMapView* mapView;
}

@end
