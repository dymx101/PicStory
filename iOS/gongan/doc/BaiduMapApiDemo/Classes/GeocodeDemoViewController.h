//
//  GeocodeDemoViewController.h
//  BaiduMapApiDemo
//
//  Copyright 2011 Baidu Inc. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "BMapKit.h"

@interface GeocodeDemoViewController : UIViewController<BMKMapViewDelegate, BMKSearchDelegate> {
	IBOutlet BMKMapView* _mapView;
	IBOutlet UITextField* _coordinateXText;
	IBOutlet UITextField* _coordinateYText;
	IBOutlet UITextField* _cityText;
	IBOutlet UITextField* _addrText;
	BMKSearch* _search;
}
-(IBAction)onClickGeocode;
-(IBAction)onClickReverseGeocode;
@end
