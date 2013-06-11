//
//  UIView+LoadFromNib.h
//  TheStoreApp
//
//  Created by Yim Daniel on 12-10-26.
//
//

#import <Foundation/Foundation.h>

@interface UIView (LoadFromNib)
+ (id)viewFromNibByDefaultClassName:(id)owner
                             option:(NSDictionary*)dic;

+ (id)viewFromNibWithOwner:(id)owner;

+ (id)viewFromNibWithOwner:(id)owner
                   atIndex:(NSUInteger)aIndex;

+ (id)viewFromNib:(NSString*)aNibName
        withOwner:(id)owner
          atIndex:(NSUInteger)aIndex;
@end
