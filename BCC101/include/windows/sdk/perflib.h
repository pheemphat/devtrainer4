/*++
Copyright (c) Microsoft Corporation.  All rights reserved.

Module Name:

    perflib.h

Abstract:

    Public headers for PERFLIB provider APIs,
--*/

#ifndef _PERFLIB_H_
#pragma option push -b -a8 -pc -A- -w-pun /*P_O_Push*/
#define _PERFLIB_H_

#pragma once
#include <winapifamily.h>

#pragma region Desktop Family
#if WINAPI_FAMILY_PARTITION(WINAPI_PARTITION_DESKTOP)


#ifdef __cplusplus
extern "C" {
#endif

// PERFLIB V2 provider side published literals, data structures and APIs.
//

// This is used in generated PERF_COUNTERSET_INFO structure to declare provider type.
// Kernel provider is reserved for Microsoft internal use.
// Driver provider and user-mode provider literals will be published.
//
#define PERF_PROVIDER_USER_MODE   0
#define PERF_PROVIDER_KERNEL_MODE 1
#define PERF_PROVIDER_DRIVER      2

// These are used for PERF_COUNTERSET_INFO::InstanceType value. That is, whether the CounterSet
// allows multiple instances (for example, Process, PhysicalDisk, etc) or only single default instance
// (for example, Memory, TCP, etc).
//
#define PERF_COUNTERSET_FLAG_MULTIPLE             2  // 0010
#define PERF_COUNTERSET_FLAG_AGGREGATE            4  // 0100
#define PERF_COUNTERSET_FLAG_HISTORY              8  // 1000
#define PERF_COUNTERSET_FLAG_INSTANCE            16  // 00010000

#define PERF_COUNTERSET_SINGLE_INSTANCE          0
#define PERF_COUNTERSET_MULTI_INSTANCES          (PERF_COUNTERSET_FLAG_MULTIPLE)
#define PERF_COUNTERSET_SINGLE_AGGREGATE         (PERF_COUNTERSET_FLAG_AGGREGATE)
#define PERF_COUNTERSET_MULTI_AGGREGATE          (PERF_COUNTERSET_FLAG_AGGREGATE | PERF_COUNTERSET_FLAG_MULTIPLE)
#define PERF_COUNTERSET_SINGLE_AGGREGATE_HISTORY (PERF_COUNTERSET_FLAG_HISTORY | PERF_COUNTERSET_SINGLE_AGGREGATE)
#define PERF_COUNTERSET_INSTANCE_AGGREGATE       (PERF_COUNTERSET_MULTI_AGGREGATE | PERF_COUNTERSET_FLAG_INSTANCE)

// Pre-defined aggregation function for CounterSets that need counter data aggregation. This is only useful
// for CounterSet with instanceType PERF_COUNTERSET_SINGLE_AGGREGATE, PERF_COUNTERSET_MULTI_AGGREGATE, and
// PERF_COUNTERSET_SINGLE_AGGREGATE_HISTORY. For other CounterSet instanceTypes, this is no effect.
//
#define PERF_AGGREGATE_UNDEFINED  0
#define PERF_AGGREGATE_TOTAL      1
#define PERF_AGGREGATE_AVG        2
#define PERF_AGGREGATE_MIN        3
#define PERF_AGGREGATE_MAX        4

// These are possible attributes used in generated PERF_COUNTER_INFO::Attrib value.
//
#define PERF_ATTRIB_BY_REFERENCE       0x0000000000000001
#define PERF_ATTRIB_NO_DISPLAYABLE     0x0000000000000002
#define PERF_ATTRIB_NO_GROUP_SEPARATOR 0x0000000000000004
#define PERF_ATTRIB_DISPLAY_AS_REAL    0x0000000000000008
#define PERF_ATTRIB_DISPLAY_AS_HEX     0x0000000000000010

// Provider counterset is defined as a leading PERF_COUNTERSET_INFO structure followed by a sequence
// of PERF_COUNTER_INFO structures. Note that the structure block will be automatically generated
// by schema generation/parsing tool.
//
typedef struct _PERF_COUNTERSET_INFO {
    GUID   CounterSetGuid;
    GUID   ProviderGuid;
    ULONG  NumCounters;
    ULONG  InstanceType;
} PERF_COUNTERSET_INFO, * PPERF_COUNTERSET_INFO;

typedef struct _PERF_COUNTER_INFO {
    ULONG      CounterId;     // max of 64K counters per GUID instance
    ULONG      Type;
    ULONGLONG  Attrib;
    ULONG      Size;
    ULONG      DetailLevel;
    LONG       Scale;
    ULONG      Offset;         // overlays to give the actual counter
} PERF_COUNTER_INFO, * PPERF_COUNTER_INFO;

// PERF_COUNTERSET_INSTANCE block is returned from PerfCreateInstance() API call to identify specific
// instance of a counterset. The returned block is formed by PERF_COUNTERSET_INSTANCE structure followed
// by counter data block (layout defined by provider counterset template) and instance name string (if exists).
//
typedef struct _PERF_COUNTERSET_INSTANCE {
    GUID   CounterSetGuid;
    ULONG  dwSize;
    ULONG  InstanceId;
    ULONG  InstanceNameOffset;
    ULONG  InstanceNameSize;
} PERF_COUNTERSET_INSTANCE, * PPERF_COUNTERSET_INSTANCE;

// PERF_COUNTER_IDENTITY structure is used in customized notification callback. Wheneven PERFLIB V2
// invokes customized notification callback, it passes wnode datablock (which contains WNODE_HEADER
// structure followed by other binary data) that contains the information providers can use.
//
// For PERF_ADD_COUNTER and PERF_REMOVE_COUNTER request, PERFLIB will pass PERF_COUNTER_IDENTITY block
// so that providers know which counter is added/removed. For other requests, currently only machine name 
// is passed (so that providers can determine whether the request is for physical node or virtual node).
//
typedef struct _PERF_COUNTER_IDENTITY {
    GUID   CounterSetGuid;
    ULONG  BufferSize;
    ULONG  CounterId;
    ULONG  InstanceId;
    ULONG  MachineOffset;
    ULONG  NameOffset;
    ULONG  Reserved;
} PERF_COUNTER_IDENTITY, * PPERF_COUNTER_IDENTITY;

#define PERF_WILDCARD_COUNTER   0xFFFFFFFF
#define PERF_WILDCARD_INSTANCE  L"*"
#define PERF_AGGREGATE_INSTANCE L"_Total"
#define PERF_MAX_INSTANCE_NAME  1024

#define PERF_ADD_COUNTER            1
#define PERF_REMOVE_COUNTER         2
#define PERF_ENUM_INSTANCES         3
#define PERF_COLLECT_START          5
#define PERF_COLLECT_END            6
#define PERF_FILTER                 9

// Prototype for service request callback. Data providers register with PERFLIB V2 by passing a service
// request callback function that is called for all PERFLIB requests.
//
typedef ULONG (
#ifndef MIDL_PASS
WINAPI
#endif
* PERFLIBREQUEST)(
    IN ULONG  RequestCode,
    IN PVOID  Buffer,
    IN ULONG  BufferSize
);

// Usually PerfSetCounterSetInfo() calls is automatically generated PerfAutoStartUp() function (generated
// by schema generation/parsing tool) to inform PERFLIB the layout of specific counterset.
//
ULONG __stdcall
PerfStartProvider(
    _In_     LPGUID          ProviderGuid,
    _In_opt_ PERFLIBREQUEST  ControlCallback,
    _Out_    HANDLE        * phProvider
);

// Start PERFLIB V2 provider with customized memory allocation/free routines.
//
typedef LPVOID (* PERF_MEM_ALLOC)(IN SIZE_T AllocSize, IN LPVOID pContext);
typedef void (* PERF_MEM_FREE)(IN LPVOID pBuffer, IN LPVOID pContext);

typedef struct _PROVIDER_CONTEXT {
    DWORD          ContextSize; // should be sizeof(PERF_PROVIDER_CONTEXT)
    DWORD          Reserved;
    PERFLIBREQUEST ControlCallback;
    PERF_MEM_ALLOC MemAllocRoutine;
    PERF_MEM_FREE  MemFreeRoutine;
    LPVOID         pMemContext;
} PERF_PROVIDER_CONTEXT, * PPERF_PROVIDER_CONTEXT;

ULONG WINAPI
PerfStartProviderEx(
    _In_ LPGUID ProviderGuid,
    _In_opt_ PPERF_PROVIDER_CONTEXT ProviderContext,
    _Out_ PHANDLE Provider
    );

ULONG WINAPI
PerfStartProvider(
    _In_ LPGUID ProviderGuid,
    _In_opt_ PERFLIBREQUEST ControlCallback,
    _Out_ PHANDLE Provider
    );

ULONG WINAPI
PerfStopProvider(
    _In_ HANDLE ProviderHandle
    );

ULONG WINAPI
PerfSetCounterSetInfo(
    _In_ HANDLE ProviderHandle,
    _Inout_updates_bytes_(TemplateSize) PPERF_COUNTERSET_INFO Template,
    _In_ ULONG TemplateSize
    );

PPERF_COUNTERSET_INSTANCE WINAPI
PerfCreateInstance(
    _In_ HANDLE ProviderHandle,
    _In_ LPCGUID CounterSetGuid,
    _In_ PCWSTR Name,
    _In_ ULONG Id
    );

ULONG WINAPI
PerfDeleteInstance(
    _In_ HANDLE Provider,
    _In_ PPERF_COUNTERSET_INSTANCE InstanceBlock
    );

PPERF_COUNTERSET_INSTANCE WINAPI
PerfQueryInstance(
    _In_ HANDLE ProviderHandle,
    _In_ LPCGUID CounterSetGuid,
    _In_ PCWSTR Name,
    _In_ ULONG Id
    );

ULONG WINAPI
PerfSetCounterRefValue(
    _In_ HANDLE Provider,
    _Inout_ PPERF_COUNTERSET_INSTANCE Instance,
    _In_ ULONG CounterId,
    _In_ PVOID Address
    );

ULONG WINAPI
PerfSetULongCounterValue(
    _In_ HANDLE Provider,
    _Inout_ PPERF_COUNTERSET_INSTANCE Instance,
    _In_ ULONG CounterId,
    _In_ ULONG Value
    );

ULONG WINAPI
PerfSetULongLongCounterValue(
    _In_ HANDLE Provider,
    _Inout_ PPERF_COUNTERSET_INSTANCE Instance,
    _In_ ULONG CounterId,
    _In_ ULONGLONG Value
    );

ULONG WINAPI
PerfIncrementULongCounterValue(
    _In_ HANDLE Provider,
    _Inout_ PPERF_COUNTERSET_INSTANCE Instance,
    _In_ ULONG CounterId,
    _In_ ULONG Value
    );

ULONG WINAPI
PerfIncrementULongLongCounterValue(
    _In_ HANDLE Provider,
    _Inout_ PPERF_COUNTERSET_INSTANCE Instance,
    _In_ ULONG CounterId,
    _In_ ULONGLONG Value
    );

ULONG WINAPI
PerfDecrementULongCounterValue(
    _In_ HANDLE Provider,
    _Inout_ PPERF_COUNTERSET_INSTANCE Instance,
    _In_ ULONG CounterId,
    _In_ ULONG Value
    );

ULONG WINAPI
PerfDecrementULongLongCounterValue(
    _In_ HANDLE Provider,
    _Inout_ PPERF_COUNTERSET_INSTANCE Instance,
    _In_ ULONG CounterId,
    _In_ ULONGLONG Value
    );

#ifdef __cplusplus
}       // extern "C"
#endif


#endif /* WINAPI_FAMILY_PARTITION(WINAPI_PARTITION_DESKTOP) */
#pragma endregion

#pragma option pop /*P_O_Pop*/
#endif /* _PERFLIB_H_ */
