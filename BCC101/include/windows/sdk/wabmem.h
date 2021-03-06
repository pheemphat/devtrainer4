//
//    Copyright (C) Microsoft.  All rights reserved.
//
#if !defined(WABMEM_H)
#pragma option push -b -a8 -pc -A- -w-pun /*P_O_Push*/
#define WABMEM_H

#include <winapifamily.h>

#pragma region Desktop Family
#if WINAPI_FAMILY_PARTITION(WINAPI_PARTITION_DESKTOP)

#if !defined(MAPIX_H)
typedef SCODE (STDMETHODCALLTYPE MAPIALLOCATEBUFFER)(
        _In_                       ULONG      cbSize,
        _Outptr_result_bytebuffer_(cbSize) LPVOID FAR *lppBuffer
);

typedef SCODE (STDMETHODCALLTYPE MAPIALLOCATEMORE)(
	ULONG			cbSize,
	LPVOID			lpObject,
	LPVOID FAR *	lppBuffer
);

typedef ULONG (STDAPICALLTYPE MAPIFREEBUFFER)(
	LPVOID			lpBuffer
);

typedef MAPIALLOCATEBUFFER FAR	*LPMAPIALLOCATEBUFFER;
typedef MAPIALLOCATEMORE FAR	*LPMAPIALLOCATEMORE;
typedef MAPIFREEBUFFER FAR 		*LPMAPIFREEBUFFER;

#endif  // MAPIX_H

typedef SCODE (STDMETHODCALLTYPE WABALLOCATEBUFFER)(
   LPWABOBJECT     lpWABObject,
	ULONG			cbSize,
	LPVOID FAR *	lppBuffer
);

typedef SCODE (STDMETHODCALLTYPE WABALLOCATEMORE)(
   LPWABOBJECT     lpWABObject,
	ULONG			cbSize,
	LPVOID			lpObject,
	LPVOID FAR *	lppBuffer
);

typedef ULONG (STDAPICALLTYPE WABFREEBUFFER)(
   LPWABOBJECT     lpWABObject,
	LPVOID			lpBuffer
);

typedef WABALLOCATEBUFFER FAR	*LPWABALLOCATEBUFFER;
typedef WABALLOCATEMORE FAR	*LPWABALLOCATEMORE;
typedef WABFREEBUFFER FAR 		*LPWABFREEBUFFER;

#endif /* WINAPI_FAMILY_PARTITION(WINAPI_PARTITION_DESKTOP) */
#pragma endregion

#pragma option pop /*P_O_Pop*/
#endif  // WABMEM_H
