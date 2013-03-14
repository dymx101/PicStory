//
//  MyAnnotation.m
//  LbsDemo
//
//  Created by Yim Daniel on 13-2-26.
//  Copyright (c) 2013年 Yim Daniel. All rights reserved.
//

#import "MyAnnotation.h"

@implementation MyAnnotation

-(id)initWithCoordinates:(CLLocationCoordinate2D)paramCoordinates title:(NSString *)paramTitle subTitle:(NSString *)paramSubTitle
{
    self = [super init];
    if (self) {
        _coordinate = paramCoordinates;
        _title = paramTitle;
        _subtitle = paramSubTitle;
        _pinColor = MKPinAnnotationColorGreen;
    }
    
    return self;
}

+(NSString *)reusableIdentiferforPinColor:(MKPinAnnotationColor)paramColor
{
    NSString *result = nil;
    switch (paramColor) {
        case MKPinAnnotationColorRed:
        {
            result = REUSABLE_PIN_RED;
        }
            break;
            
        case MKPinAnnotationColorGreen:
        {
            result = REUSABLE_PIN_GREEN;
        }
            break;
            
        case MKPinAnnotationColorPurple:
        {
            result = REUSABLE_PIN_PURPLE;
        }
            break;
            
        default:
            break;
    }
    
    return result;
}

@end
