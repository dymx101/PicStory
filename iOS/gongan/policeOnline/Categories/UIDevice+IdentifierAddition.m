//
//  UIDevice(Identifier).m
//  UIDeviceAddition
//
//  Created by Georg Kitz on 20.08.11.
//  Copyright 2011 Aurora Apps. All rights reserved.
//

#import "UIDevice+IdentifierAddition.h"
#import "NSString+MD5Addition.h"

#include <sys/socket.h> // Per msqr
#include <sys/sysctl.h>
#include <net/if.h>
#include <net/if_dl.h>

//@interface UIDevice(Private)
//
//- (NSString *) macaddress;
//
//@end

@implementation UIDevice (IdentifierAddition)

////////////////////////////////////////////////////////////////////////////////
#pragma mark -
#pragma mark Private Methods

// Return the local MAC addy
// Courtesy of FreeBSD hackers email list
// Accidentally munged during previous update. Fixed thanks to erica sadun & mlamb.
+ (NSString *) macaddress{
    
    int                 mib[6];
    size_t              len;
    char                *buf;
    unsigned char       *ptr;
    struct if_msghdr    *ifm;
    struct sockaddr_dl  *sdl;
    
    mib[0] = CTL_NET;
    mib[1] = AF_ROUTE;
    mib[2] = 0;
    mib[3] = AF_LINK;
    mib[4] = NET_RT_IFLIST;
    
    if ((mib[5] = if_nametoindex("en0")) == 0) {
        printf("Error: if_nametoindex error\n");
        return NULL;
    }
    
    if (sysctl(mib, 6, NULL, &len, NULL, 0) < 0) {
        printf("Error: sysctl, take 1\n");
        return NULL;
    }
    
    if ((buf = malloc(len)) == NULL) {
        printf("Could not allocate memory. error!\n");
        return NULL;
    }
    
    if (sysctl(mib, 6, buf, &len, NULL, 0) < 0) {
        printf("Error: sysctl, take 2");
        free(buf);
        return NULL;
    }
    
    ifm = (struct if_msghdr *)buf;
    sdl = (struct sockaddr_dl *)(ifm + 1);
    ptr = (unsigned char *)LLADDR(sdl);
    NSString *outstring = [NSString stringWithFormat:@"%02X:%02X:%02X:%02X:%02X:%02X", 
                           *ptr, *(ptr+1), *(ptr+2), *(ptr+3), *(ptr+4), *(ptr+5)];
    free(buf);
    
    return outstring;
}

////////////////////////////////////////////////////////////////////////////////
#pragma mark -
#pragma mark Public Methods

+ (NSString *) uniqueDeviceIdentifier{
    NSString *macaddress = [UIDevice macaddress];
    NSString *bundleIdentifier = [[NSBundle mainBundle] bundleIdentifier];
    
    NSString *stringToHash = [NSString stringWithFormat:@"%@%@",macaddress,bundleIdentifier];
    NSString *uniqueIdentifier = [stringToHash stringFromMD5];
    
    return uniqueIdentifier;
}

+ (NSString *) uniqueGlobalDeviceIdentifier{
    NSString *macaddress = [UIDevice macaddress];
    NSString *uniqueIdentifier = [macaddress stringFromMD5];
    
    return uniqueIdentifier;
}

+ (NSString *) platform{
    
    size_t size;
    
    sysctlbyname("hw.machine", NULL, &size, NULL, 0);
    
    char *machine = malloc(size);
    
    sysctlbyname("hw.machine", machine, &size, NULL, 0);
    
    NSString *platform = [NSString stringWithCString:machine encoding:NSUTF8StringEncoding];
    
    free(machine);
    
    return platform;
    
}

+(NSString *) platformString{
    
    NSString *platform = [self platform];
    /*
     AppleTV(2G)    (AppleTV2,1)
     iPad           (iPad1,1)
     iPad2,1        (iPad2,1)Wifi版
     iPad2,2        (iPad2,2)GSM3G版
     iPad2,3        (iPad2,3)CDMA3G版
     iPhone         (iPhone1,1)
     iPhone3G       (iPhone1,2)
     iPhone3GS      (iPhone2,1)
     iPhone4        (iPhone3,1)
     iPhone4(vz)    (iPhone3,3)iPhone4 CDMA版
     iPhone4S       (iPhone4,1)
     iPodTouch(1G)  (iPod1,1)
     iPodTouch(2G)  (iPod2,1)
     iPodTouch(3G)  (iPod3,1)
     iPodTouch(4G)  (iPod4,1)
     */
    if ([platform isEqualToString:@"iPhone1,1"])    return @"iPhone 1G";
    
    if ([platform isEqualToString:@"iPhone1,2"])    return @"iPhone 3G";
    
    if ([platform isEqualToString:@"iPhone2,1"])    return @"iPhone 3GS";
    
    if ([platform isEqualToString:@"iPhone3,1"])    return @"iPhone 4";
    
    if ([platform isEqualToString:@"iPod1,1"])      return @"iPod Touch 1G";
    
    if ([platform isEqualToString:@"iPod2,1"])      return @"iPod Touch 2G";
    
    if ([platform isEqualToString:@"iPod3,1"])      return @"iPod Touch 3G";
    
    if ([platform isEqualToString:@"iPod4,1"])      return @"iPod Touch 4G";
    
    if ([platform hasPrefix:@"iPad1"])              return @"iPad";
    if ([platform hasPrefix:@"iPad2"])              return @"iPad2";
    if ([platform hasPrefix:@"iPad3"])              return @"iPad3";

//    if ([platform isEqualToString:@"iPad1,1"])      return @"iPad";
//    
//    if ([platform isEqualToString:@"iPad2,1"])      return @"iPad2";
//    
//    if ([platform isEqualToString:@"iPad3,1"])      return @"iPad3";

    if ([platform isEqualToString:@"i386"])         return @"Simulator";
    
    return platform;
    
}
@end
