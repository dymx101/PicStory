//
//  UIView+LoadFromNib.m
//  TheStoreApp
//
//  Created by Yim Daniel on 12-10-26.
//
//

#import "UIView+LoadFromNib.h"

@implementation UIView (LoadFromNib)
+ (id)viewFromNibByDefaultClassName:(id)owner option:(NSDictionary*)dic
{
	return [[[NSBundle mainBundle] loadNibNamed:NSStringFromClass([self class]) owner:owner options:dic]lastObject];
}

+ (id)viewFromNibWithOwner:(id)owner
{
	return [self viewFromNibWithOwner:owner atIndex:0];
}

+ (id)viewFromNibWithOwner:(id)owner atIndex:(NSUInteger)aIndex
{
	return [[[NSBundle mainBundle] loadNibNamed:NSStringFromClass([self class]) owner:owner options:nil] objectAtIndex:aIndex];
}

+ (id)viewFromNib:(NSString*)aNibName withOwner:(id)owner atIndex:(NSUInteger)aIndex
{
	return [[[NSBundle mainBundle] loadNibNamed:aNibName owner:owner options:nil] objectAtIndex:aIndex];
}

@end
