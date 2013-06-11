//
//  PoiSearchDemoViewController.h
//  BaiduMapApiDemo
//
//  Copyright 2011 Baidu Inc. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "BMapKit.h"

@interface PoiSearchDemoViewController : UIViewController<BMKMapViewDelegate, BMKSearchDelegate> {
	IBOutlet BMKMapView* _mapView;
	IBOutlet UITextField* _cityText;
	IBOutlet UITextField* _keyText; 
	BMKSearch* _search;
}

-(IBAction)onClickOk;

@end
