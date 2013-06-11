//
//  BusLineSearchViewController.m
//  BaiduMapApiDemoSrc
//
//  Created by baidu on 12-6-20.
//  Copyright (c) 2012年 __MyCompanyName__. All rights reserved.
//

#import "BusLineSearchViewController.h"
#define MYBUNDLE_NAME @ "mapapi.bundle"
#define MYBUNDLE_PATH [[[NSBundle mainBundle] resourcePath] stringByAppendingPathComponent: MYBUNDLE_NAME]
#define MYBUNDLE [NSBundle bundleWithPath: MYBUNDLE_PATH]
@interface RouteAnnotation : BMKPointAnnotation
{
	int _type; ///<0:起点 1：终点 2：公交 3：地铁 4:驾乘
	int _degree;
}

@property (nonatomic) int type;
@property (nonatomic) int degree;
@end

@implementation BusLineSearchViewController


- (NSString*)getMyBundlePath1:(NSString *)filename
{
	
	NSBundle * libBundle = MYBUNDLE ;
	if ( libBundle && filename ){
		NSString * s=[[libBundle resourcePath ] stringByAppendingPathComponent : filename];
		NSLog ( @"%@" ,s);
		return s;
	}
	return nil ;
}

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
    }
    return self;
}

- (void)didReceiveMemoryWarning
{
    // Releases the view if it doesn't have a superview.
    [super didReceiveMemoryWarning];
    
    // Release any cached data, images, etc that aren't in use.
}
- (BMKAnnotationView*)getRouteAnnotationView:(BMKMapView *)mapview viewForAnnotation:(RouteAnnotation*)routeAnnotation
{
	BMKAnnotationView* view = nil;
	switch (routeAnnotation.type) {
		case 0:
		{
			view = [mapview dequeueReusableAnnotationViewWithIdentifier:@"start_node"];
			if (view == nil) {
				view = [[BMKAnnotationView alloc]initWithAnnotation:routeAnnotation reuseIdentifier:@"start_node"];
				view.image = [UIImage imageWithContentsOfFile:[self getMyBundlePath1:@"images/icon_nav_start.png"]];
				view.centerOffset = CGPointMake(0, -(view.frame.size.height * 0.5));
				view.canShowCallout = TRUE;
			}
			view.annotation = routeAnnotation;
		}
			break;
		case 1:
		{
			view = [mapview dequeueReusableAnnotationViewWithIdentifier:@"end_node"];
			if (view == nil) {
				view = [[BMKAnnotationView alloc]initWithAnnotation:routeAnnotation reuseIdentifier:@"end_node"];
				view.image = [UIImage imageWithContentsOfFile:[self getMyBundlePath1:@"images/icon_nav_end.png"]];
				view.centerOffset = CGPointMake(0, -(view.frame.size.height * 0.5));
				view.canShowCallout = TRUE;
			}
			view.annotation = routeAnnotation;
		}
			break;
		case 2:
		{
			view = [mapview dequeueReusableAnnotationViewWithIdentifier:@"bus_node"];
			if (view == nil) {
				view = [[BMKAnnotationView alloc]initWithAnnotation:routeAnnotation reuseIdentifier:@"bus_node"];
				view.image = [UIImage imageWithContentsOfFile:[self getMyBundlePath1:@"images/icon_nav_bus.png"]];
				view.canShowCallout = TRUE;
			}
			view.annotation = routeAnnotation;
		}
			break;
		case 3:
		{
			view = [mapview dequeueReusableAnnotationViewWithIdentifier:@"rail_node"];
			if (view == nil) {
				view = [[BMKAnnotationView alloc]initWithAnnotation:routeAnnotation reuseIdentifier:@"rail_node"];
				view.image = [UIImage imageWithContentsOfFile:[self getMyBundlePath1:@"images/icon_nav_rail.png"]];
				view.canShowCallout = TRUE;
			}
			view.annotation = routeAnnotation;
		}
			break;
		case 4:
		{
			view = [mapview dequeueReusableAnnotationViewWithIdentifier:@"route_node"];
			if (view == nil) {
				view = [[BMKAnnotationView alloc]initWithAnnotation:routeAnnotation reuseIdentifier:@"route_node"];
				view.canShowCallout = TRUE;
			} else {
				[view setNeedsDisplay];
			}
			
			UIImage* image = [UIImage imageWithContentsOfFile:[self getMyBundlePath1:@"images/icon_direction.png"]];
			view.image = [image imageRotatedByDegrees:routeAnnotation.degree];
			view.annotation = routeAnnotation;
			
		}
			break;
		default:
			break;
	}
	
	return view;
}
- (BMKAnnotationView *)mapView:(BMKMapView *)view viewForAnnotation:(id <BMKAnnotation>)annotation
{
	if ([annotation isKindOfClass:[RouteAnnotation class]]) {
		return [self getRouteAnnotationView:view viewForAnnotation:(RouteAnnotation*)annotation];
	}
	return nil;
}

- (BMKOverlayView*)mapView:(BMKMapView *)map viewForOverlay:(id<BMKOverlay>)overlay
{	
	if ([overlay isKindOfClass:[BMKPolyline class]]) {
        BMKPolylineView* polylineView = [[[BMKPolylineView alloc] initWithOverlay:overlay] autorelease];
        polylineView.fillColor = [[UIColor cyanColor] colorWithAlphaComponent:1];
        polylineView.strokeColor = [[UIColor blueColor] colorWithAlphaComponent:0.7];
        polylineView.lineWidth = 3.0;
        return polylineView;
    }
	return nil;
}


- (void)onGetPoiResult:(NSArray*)poiResultList searchType:(int)type errorCode:(int)error
{
	if (error == BMKErrorOk) {
		BMKPoiResult* result = [poiResultList objectAtIndex:0];
        BMKPoiInfo* poi;
		for (int i = 0; i < result.poiInfoList.count; i++) {
			poi = [result.poiInfoList objectAtIndex:i];
            if (poi.epoitype == 2) {
                
                break;
            }

		}
        //开始bueline详情搜索
        if(poi != nil && poi.epoitype == 2 )
        {
            NSLog(poi.uid);
            BOOL flag = [_search busLineSearch:_cityText.text withKey:poi.uid];
            if (!flag) {
                NSLog(@"search failed!");
            }
        }
	}
}
-(void)onGetBusDetailResult:(BMKBusLineResult *)busLineResult errorCode:(int)error
{
    if (error == BMKErrorOk) {
        NSLog(@"onGetTransitRouteResult:error:%d", error);
        NSLog(@"busname:%@",[busLineResult mBusName]);

        //NSLog(@"%2f",[result.mBusRoute getPoints:0]->x);
        NSLog(@"%2f",[busLineResult.mBusRoute getPoints:0]->x);
        
        //起点
        RouteAnnotation* item = [[RouteAnnotation alloc]init];
       
        item.coordinate = busLineResult.mBusRoute.startPt;
        BMKStep* tempstep = [busLineResult.mBusRoute.steps objectAtIndex:0];

        item.title = tempstep.content;
        
        item.type = 0;
        [_mapView addAnnotation:item];
        [item release];
        NSLog(@"%2f",[busLineResult.mBusRoute getPoints:0]->x);
        
        //终点
        item = [[RouteAnnotation alloc]init];
        item.coordinate = busLineResult.mBusRoute.endPt;
        item.type = 1;
        tempstep = [busLineResult.mBusRoute.steps objectAtIndex:busLineResult.mBusRoute.steps.count-1];
        item.title = tempstep.content;
        [_mapView addAnnotation:item];
        [item release];
        NSLog(@"%2f",[busLineResult.mBusRoute getPoints:0]->x);
        
        //站点信息
        int size = 0;   
        size = busLineResult.mBusRoute.steps.count;
        for (int j = 0; j < size; j++) {
            BMKStep* step = [busLineResult.mBusRoute.steps objectAtIndex:j];
            item = [[RouteAnnotation alloc]init];
            item.coordinate = step.pt;
            item.title = step.content;
            item.degree = step.degree * 30;
            item.type = 2;
            [_mapView addAnnotation:item];
            [item release];
        }          
          
        
        //路段信息
        NSLog(@"%2f",[busLineResult.mBusRoute getPoints:0]->x);
        int index = 0;
		
		for (int i = 0; i < 1; i++) {
		
            NSLog(@"%d",busLineResult.mBusRoute.pointsCount);
			for (int j = 0; j < busLineResult.mBusRoute.pointsCount; j++) {
				int len = [busLineResult.mBusRoute getPointsNum:j];
                NSLog(@"%d",len);
				index += len;
			}
		}
		NSLog(@"%d",index);
		//BMKMapPoint* points = new BMKMapPoint[index];
        
		index = 0;
		NSLog(@"%2f",[busLineResult.mBusRoute getPoints:0]->x);
		for (int i = 0; i < 1; i++) {
		
            NSLog(@"%d",[busLineResult.mBusRoute getPointsNum:0]);
			for (int j = 0; j < busLineResult.mBusRoute.pointsCount; j++) {
				int len = [busLineResult.mBusRoute getPointsNum:j];
				//BMKMapPoint* pointArray = (BMKMapPoint*)[busLineResult.mBusRoute getPoints:j];
        
                //NSLog(@"%2f",pointArray[0].x);
				//memcpy(points + index, pointArray, len * sizeof(BMKMapPoint));
				index += len;
			}
        }
        //NSLog(@"%2f",points[0].x);
        
        
      
        //经纬度划线
//        CLLocationCoordinate2D * temppoints = new CLLocationCoordinate2D[index];
//        index = 0;
//		NSLog(@"%2f",[busLineResult.mBusRoute getPoints:0]->x);
//		for (int i = 0; i < 1; i++) {
//            
//            NSLog(@"%d",[busLineResult.mBusRoute getPointsNum:0]);
//			for (int j = 0; j < busLineResult.mBusRoute.pointsCount; j++) {
//				int len = [busLineResult.mBusRoute getPointsNum:j];
//                for(int k=0;k<len;k++)
//                {
//                    CLLocationCoordinate2D pointArray = BMKCoordinateForMapPoint([busLineResult.mBusRoute getPoints:j][k]);
//
//                        
//                    temppoints[k] = pointArray;
//                }
//                //memcpy(points + index, pointArray, len * sizeof(BMKMapPoint));
//				index += len;
//			}
//        }
//
//        BMKPolyline* polyLine = [BMKPolyline polylineWithCoordinates:temppoints count:index];
//		[_mapView addOverlay:polyLine];
//		delete []temppoints;
//        _annotation.coordinate = BMKCoordinateForMapPoint([busLineResult.mBusRoute getPoints:0][0]);
//        
//        [_mapView setCenterCoordinate:_annotation.coordinate animated:YES];
        
        
        //直角坐标划线
        BMKMapPoint * temppoints = new BMKMapPoint[index];
        index = 0;
		NSLog(@"%2f",[busLineResult.mBusRoute getPoints:0]->x);
		for (int i = 0; i < 1; i++) {
            
            NSLog(@"%d",[busLineResult.mBusRoute getPointsNum:0]);
			for (int j = 0; j < busLineResult.mBusRoute.pointsCount; j++) {
				int len = [busLineResult.mBusRoute getPointsNum:j];
                for(int k=0;k<len;k++)
                {
                    BMKMapPoint pointarray;
                    pointarray.x = [busLineResult.mBusRoute getPoints:j][k].x;
                    pointarray.y = [busLineResult.mBusRoute getPoints:j][k].y;
                   
                    
                    temppoints[k] = pointarray;
                }
                //memcpy(points + index, pointArray, len * sizeof(BMKMapPoint));
				index += len;
			}
        }
        
        BMKPolyline* polyLine = [BMKPolyline polylineWithPoints:temppoints count:index];
		[_mapView addOverlay:polyLine];
		delete []temppoints;

        _annotation.coordinate = BMKCoordinateForMapPoint([busLineResult.mBusRoute getPoints:0][0]);

        [_mapView setCenterCoordinate:_annotation.coordinate animated:YES];
            
    }
}
-(IBAction)onClickBusLineSearch
{
    NSArray* array = [NSArray arrayWithArray:_mapView.annotations];
	[_mapView removeAnnotations:array];
	array = [NSArray arrayWithArray:_mapView.overlays];
	[_mapView removeOverlays:array];
	BOOL flag = [_search poiSearchInCity:_cityText.text withKey:_busLineText.text pageIndex:0];
	if (!flag) {
		NSLog(@"search failed!");
	}
    
}
- (void)mapView:(BMKMapView *)mmapView regionDidChangeAnimated:(BOOL)animated
{
	if (animated) {
		
			[mmapView selectAnnotation:_annotation animated:YES];
		
	}
	
}
#pragma mark - View lifecycle

- (void)viewDidLoad
{
    [super viewDidLoad];
	_search = [[BMKSearch alloc]init];
    
	_cityText.text = @"北京";
	_busLineText.text  = @"717";
    
    _annotation = [[BMKPointAnnotation alloc]init];
	CLLocationCoordinate2D coor;
	coor.latitude = 39.915;
	coor.longitude = 115.404;
	_annotation.coordinate = coor;
	
	_annotation.title = @"这里是北京";
	//_annotation.subtitle = @"this is a test!";
	_annotation.subtitle = NULL;
	
	[_mapView addAnnotation:_annotation];
    // Do any additional setup after loading the view from its nib.
}


- (void)viewWillAppear:(BOOL)animated
{
    _mapView.delegate = self;
    _search.delegate = self;
}

- (void)viewDidUnload
{
    [super viewDidUnload];
    // Release any retained subviews of the main view.
    // e.g. self.myOutlet = nil;
}

- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation
{
    // Return YES for supported orientations
    return (interfaceOrientation == UIInterfaceOrientationPortrait);
}

@end
