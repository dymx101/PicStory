//
//  MyAnnotation.h
//  LbsDemo
//
//  Created by Yim Daniel on 13-2-26.
//  Copyright (c) 2013年 Yim Daniel. All rights reserved.
//

#import <Foundation/Foundation.h>

// these are the standard SDK pin colors.we are setting unique identifiers per color for each pin so that later we can reuse the pins that have already been created with the same color
#define REUSABLE_PIN_RED    @"Red"
#define REUSABLE_PIN_GREEN    @"Green"
#define REUSABLE_PIN_PURPLE    @"Purple"

@interface MyAnnotation : NSObject <MKAnnotation>
@property (nonatomic, readonly) CLLocationCoordinate2D  coordinate;
@property (nonatomic, copy, readonly) NSString *title;
@property (nonatomic, copy, readonly) NSString *subtitle;

@property (nonatomic, unsafe_unretained) MKPinAnnotationColor pinColor;

-(id)initWithCoordinates:(CLLocationCoordinate2D)paramCoordinates title:(NSString *)paramTitle subTitle:(NSString *)paramSubTitle;

+(NSString *)reusableIdentiferforPinColor:(MKPinAnnotationColor)paramColor;

@end
