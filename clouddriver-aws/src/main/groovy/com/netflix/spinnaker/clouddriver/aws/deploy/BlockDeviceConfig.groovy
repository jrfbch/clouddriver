/*
 * Copyright 2016 Netflix, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License")
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.netflix.spinnaker.clouddriver.aws.deploy

import com.netflix.spinnaker.config.AwsConfiguration.DeployDefaults
import com.netflix.spinnaker.clouddriver.aws.model.AmazonBlockDevice

class BlockDeviceConfig {

  private final DeployDefaults deployDefaults
  private final Map<String, List<AmazonBlockDevice>> blockDevicesByInstanceType

  BlockDeviceConfig(DeployDefaults deployDefaults) {
    this.deployDefaults = deployDefaults
    blockDevicesByInstanceType = [
      "a1.medium"   : sizedBlockDevicesForEbs(40),
      "a1.large"    : sizedBlockDevicesForEbs(40),
      "a1.xlarge"   : sizedBlockDevicesForEbs(80),
      "a1.2xlarge"  : sizedBlockDevicesForEbs(80),
      "a1.4xlarge"  : sizedBlockDevicesForEbs(120),
      "a1.metal"    : sizedBlockDevicesForEbs(120),

      "c1.medium"   : enumeratedBlockDevicesWithVirtualName(1),
      "c1.xlarge"   : enumeratedBlockDevicesWithVirtualName(4),

      "c3.large"    : enumeratedBlockDevicesWithVirtualName(2),
      "c3.xlarge"   : enumeratedBlockDevicesWithVirtualName(2),
      "c3.2xlarge"  : enumeratedBlockDevicesWithVirtualName(2),
      "c3.4xlarge"  : enumeratedBlockDevicesWithVirtualName(2),
      "c3.8xlarge"  : enumeratedBlockDevicesWithVirtualName(2),

      "c4.large"    : defaultBlockDevicesForEbsOnly(),
      "c4.xlarge"   : defaultBlockDevicesForEbsOnly(),
      "c4.2xlarge"  : defaultBlockDevicesForEbsOnly(),
      "c4.4xlarge"  : defaultBlockDevicesForEbsOnly(),
      "c4.8xlarge"  : defaultBlockDevicesForEbsOnly(),

      "c5.large"    : defaultBlockDevicesForEbsOnly(),
      "c5.xlarge"   : defaultBlockDevicesForEbsOnly(),
      "c5.2xlarge"  : defaultBlockDevicesForEbsOnly(),
      "c5.4xlarge"  : defaultBlockDevicesForEbsOnly(),
      "c5.9xlarge"  : defaultBlockDevicesForEbsOnly(),
      "c5.12xlarge" : defaultBlockDevicesForEbsOnly(),
      "c5.18xlarge" : defaultBlockDevicesForEbsOnly(),
      "c5.24xlarge" : defaultBlockDevicesForEbsOnly(),

      "c5d.large"    : enumeratedBlockDevicesWithVirtualName(1),
      "c5d.xlarge"   : enumeratedBlockDevicesWithVirtualName(1),
      "c5d.2xlarge"  : enumeratedBlockDevicesWithVirtualName(1),
      "c5d.4xlarge"  : enumeratedBlockDevicesWithVirtualName(1),
      "c5d.9xlarge"  : enumeratedBlockDevicesWithVirtualName(1),
      "c5d.12xlarge" : enumeratedBlockDevicesWithVirtualName(2),
      "c5d.18xlarge" : enumeratedBlockDevicesWithVirtualName(2),
      "c5d.24xlarge" : enumeratedBlockDevicesWithVirtualName(4),
      "c5d.metal"    : enumeratedBlockDevicesWithVirtualName(4),

      "c5n.large"   : defaultBlockDevicesForEbsOnly(),
      "c5n.xlarge"  : defaultBlockDevicesForEbsOnly(),
      "c5n.2xlarge" : defaultBlockDevicesForEbsOnly(),
      "c5n.4xlarge" : defaultBlockDevicesForEbsOnly(),
      "c5n.9xlarge" : defaultBlockDevicesForEbsOnly(),
      "c5n.18xlarge": defaultBlockDevicesForEbsOnly(),
      "c5n.metal"   : defaultBlockDevicesForEbsOnly(),

      "c5a.large"    : sizedBlockDevicesForEbs(40),
      "c5a.xlarge"   : sizedBlockDevicesForEbs(80),
      "c5a.2xlarge"  : sizedBlockDevicesForEbs(80),
      "c5a.4xlarge"  : sizedBlockDevicesForEbs(120),
      "c5a.8xlarge"  : sizedBlockDevicesForEbs(120),
      "c5a.12xlarge" : sizedBlockDevicesForEbs(120),
      "c5a.16xlarge" : sizedBlockDevicesForEbs(120),
      "c5a.24xlarge" : sizedBlockDevicesForEbs(120),

      "c6g.medium"   : sizedBlockDevicesForEbs(40),
      "c6g.large"    : sizedBlockDevicesForEbs(40),
      "c6g.xlarge"   : sizedBlockDevicesForEbs(80),
      "c6g.2xlarge"  : sizedBlockDevicesForEbs(80),
      "c6g.4xlarge"  : sizedBlockDevicesForEbs(120),
      "c6g.8xlarge"  : sizedBlockDevicesForEbs(120),
      "c6g.12xlarge" : sizedBlockDevicesForEbs(120),
      "c6g.16xlarge" : sizedBlockDevicesForEbs(120),
      "c6g.metal"    : sizedBlockDevicesForEbs(120),

      "c6gd.medium"   : enumeratedBlockDevicesWithVirtualName(1),
      "c6gd.large"    : enumeratedBlockDevicesWithVirtualName(1),
      "c6gd.xlarge"   : enumeratedBlockDevicesWithVirtualName(1),
      "c6gd.2xlarge"  : enumeratedBlockDevicesWithVirtualName(1),
      "c6gd.4xlarge"  : enumeratedBlockDevicesWithVirtualName(1),
      "c6gd.8xlarge"  : enumeratedBlockDevicesWithVirtualName(1),
      "c6gd.12xlarge" : enumeratedBlockDevicesWithVirtualName(2),
      "c6gd.16xlarge" : enumeratedBlockDevicesWithVirtualName(2),
      "c6gd.metal"    : enumeratedBlockDevicesWithVirtualName(2),

      "cc2.8xlarge" : enumeratedBlockDevicesWithVirtualName(4),

      "cg1.4xlarge" : sizedBlockDevicesForEbs(120),

      "cr1.8xlarge" : enumeratedBlockDevicesWithVirtualName(2),

      "d2.xlarge"   : enumeratedBlockDevicesWithVirtualName(3),
      "d2.2xlarge"  : enumeratedBlockDevicesWithVirtualName(6),
      "d2.4xlarge"  : enumeratedBlockDevicesWithVirtualName(12),
      "d2.8xlarge"  : enumeratedBlockDevicesWithVirtualName(24),

      "f1.2xlarge"  : enumeratedBlockDevicesWithVirtualName(1),
      "f1.4xlarge"  : enumeratedBlockDevicesWithVirtualName(1),
      "f1.16xlarge" : enumeratedBlockDevicesWithVirtualName(4),

      "g2.2xlarge"  : enumeratedBlockDevicesWithVirtualName(1),
      "g2.8xlarge"  : enumeratedBlockDevicesWithVirtualName(2),

      "g3s.xlarge"  : sizedBlockDevicesForEbs(80),
      "g3.4xlarge"  : sizedBlockDevicesForEbs(120),
      "g3.8xlarge"  : sizedBlockDevicesForEbs(120),
      "g3.16xlarge" : sizedBlockDevicesForEbs(120),

      "g4dn.xlarge"   : enumeratedBlockDevicesWithVirtualName(1),
      "g4dn.2xlarge"  : enumeratedBlockDevicesWithVirtualName(1),
      "g4dn.4xlarge"  : enumeratedBlockDevicesWithVirtualName(1),
      "g4dn.8xlarge"  : enumeratedBlockDevicesWithVirtualName(1),
      "g4dn.12xlarge" : enumeratedBlockDevicesWithVirtualName(1),
      "g4dn.16xlarge" : enumeratedBlockDevicesWithVirtualName(1),
      "g4dn.metal"    : enumeratedBlockDevicesWithVirtualName(2),

      "h1.2xlarge"  : enumeratedBlockDevicesWithVirtualName(1),
      "h1.4xlarge"  : enumeratedBlockDevicesWithVirtualName(2),
      "h1.8xlarge"  : enumeratedBlockDevicesWithVirtualName(4),
      "h1.16xlarge" : enumeratedBlockDevicesWithVirtualName(8),

      "hs1.8xlarge" : enumeratedBlockDevicesWithVirtualName(24),

      "i2.xlarge"   : enumeratedBlockDevicesWithVirtualName(1),
      "i2.2xlarge"  : enumeratedBlockDevicesWithVirtualName(2),
      "i2.4xlarge"  : enumeratedBlockDevicesWithVirtualName(4),
      "i2.8xlarge"  : enumeratedBlockDevicesWithVirtualName(8),

      "i3.large"    : enumeratedBlockDevicesWithVirtualName(1),
      "i3.xlarge"   : enumeratedBlockDevicesWithVirtualName(1),
      "i3.2xlarge"  : enumeratedBlockDevicesWithVirtualName(1),
      "i3.4xlarge"  : enumeratedBlockDevicesWithVirtualName(2),
      "i3.8xlarge"  : enumeratedBlockDevicesWithVirtualName(4),
      "i3.16xlarge" : enumeratedBlockDevicesWithVirtualName(8),
      "i3.metal"    : enumeratedBlockDevicesWithVirtualName(8),

      "i3en.large"  : enumeratedBlockDevicesWithVirtualName(1),
      "i3en.xlarge" : enumeratedBlockDevicesWithVirtualName(1),
      "i3en.2xlarge": enumeratedBlockDevicesWithVirtualName(2),
      "i3en.3xlarge": enumeratedBlockDevicesWithVirtualName(1),
      "i3en.6xlarge": enumeratedBlockDevicesWithVirtualName(2),
      "i3en.12xlarge": enumeratedBlockDevicesWithVirtualName(4),
      "i3en.24xlarge": enumeratedBlockDevicesWithVirtualName(8),

      "inf1.xlarge"   : sizedBlockDevicesForEbs(80),
      "inf1.2xlarge"  : sizedBlockDevicesForEbs(80),
      "inf1.6xlarge"  : sizedBlockDevicesForEbs(120),
      "inf1.24xlarge" : sizedBlockDevicesForEbs(120),

      "m1.small"    : enumeratedBlockDevicesWithVirtualName(1),
      "m1.medium"   : enumeratedBlockDevicesWithVirtualName(1),
      "m1.large"    : enumeratedBlockDevicesWithVirtualName(2),
      "m1.xlarge"   : enumeratedBlockDevicesWithVirtualName(4),

      "m2.xlarge"   : enumeratedBlockDevicesWithVirtualName(1),
      "m2.2xlarge"  : enumeratedBlockDevicesWithVirtualName(1),
      "m2.4xlarge"  : enumeratedBlockDevicesWithVirtualName(2),

      "m3.medium"   : enumeratedBlockDevicesWithVirtualName(1),
      "m3.large"    : enumeratedBlockDevicesWithVirtualName(1),
      "m3.xlarge"   : enumeratedBlockDevicesWithVirtualName(2),
      "m3.2xlarge"  : enumeratedBlockDevicesWithVirtualName(2),

      "m4.large"    : sizedBlockDevicesForEbs(40),
      "m4.xlarge"   : sizedBlockDevicesForEbs(80),
      "m4.2xlarge"  : sizedBlockDevicesForEbs(80),
      "m4.4xlarge"  : sizedBlockDevicesForEbs(120),
      "m4.10xlarge" : sizedBlockDevicesForEbs(120),
      "m4.16xlarge" : sizedBlockDevicesForEbs(120),

      "m5.large"    : sizedBlockDevicesForEbs(40),
      "m5.xlarge"   : sizedBlockDevicesForEbs(80),
      "m5.2xlarge"  : sizedBlockDevicesForEbs(80),
      "m5.4xlarge"  : sizedBlockDevicesForEbs(120),
      "m5.8xlarge"  : sizedBlockDevicesForEbs(120),
      "m5.12xlarge" : sizedBlockDevicesForEbs(120),
      "m5.16xlarge" : sizedBlockDevicesForEbs(120),
      "m5.24xlarge" : sizedBlockDevicesForEbs(120),

      "m5d.large"    : enumeratedBlockDevicesWithVirtualName(1),
      "m5d.xlarge"   : enumeratedBlockDevicesWithVirtualName(1),
      "m5d.2xlarge"  : enumeratedBlockDevicesWithVirtualName(1),
      "m5d.4xlarge"  : enumeratedBlockDevicesWithVirtualName(2),
      "m5d.8xlarge"  : enumeratedBlockDevicesWithVirtualName(2),
      "m5d.12xlarge" : enumeratedBlockDevicesWithVirtualName(2),
      "m5d.16xlarge" : enumeratedBlockDevicesWithVirtualName(4),
      "m5d.24xlarge" : enumeratedBlockDevicesWithVirtualName(4),

      "m5n.large"    : sizedBlockDevicesForEbs(40),
      "m5n.xlarge"   : sizedBlockDevicesForEbs(80),
      "m5n.2xlarge"  : sizedBlockDevicesForEbs(80),
      "m5n.4xlarge"  : sizedBlockDevicesForEbs(120),
      "m5n.8xlarge"  : sizedBlockDevicesForEbs(120),
      "m5n.12xlarge" : sizedBlockDevicesForEbs(120),
      "m5n.16xlarge" : sizedBlockDevicesForEbs(120),
      "m5n.24xlarge" : sizedBlockDevicesForEbs(120),

      "m5dn.large"    : enumeratedBlockDevicesWithVirtualName(1),
      "m5dn.xlarge"   : enumeratedBlockDevicesWithVirtualName(1),
      "m5dn.2xlarge"  : enumeratedBlockDevicesWithVirtualName(1),
      "m5dn.4xlarge"  : enumeratedBlockDevicesWithVirtualName(2),
      "m5dn.8xlarge"  : enumeratedBlockDevicesWithVirtualName(2),
      "m5dn.12xlarge" : enumeratedBlockDevicesWithVirtualName(2),
      "m5dn.16xlarge" : enumeratedBlockDevicesWithVirtualName(4),
      "m5dn.24xlarge" : enumeratedBlockDevicesWithVirtualName(4),

      "m5a.large"    : sizedBlockDevicesForEbs(40),
      "m5a.xlarge"   : sizedBlockDevicesForEbs(80),
      "m5a.2xlarge"  : sizedBlockDevicesForEbs(80),
      "m5a.4xlarge"  : sizedBlockDevicesForEbs(120),
      "m5a.8xlarge"  : sizedBlockDevicesForEbs(120),
      "m5a.12xlarge" : sizedBlockDevicesForEbs(120),
      "m5a.16xlarge" : sizedBlockDevicesForEbs(120),
      "m5a.24xlarge" : sizedBlockDevicesForEbs(120),

      "m5ad.large"    : enumeratedBlockDevicesWithVirtualName(1),
      "m5ad.xlarge"   : enumeratedBlockDevicesWithVirtualName(1),
      "m5ad.2xlarge"  : enumeratedBlockDevicesWithVirtualName(1),
      "m5ad.4xlarge"  : enumeratedBlockDevicesWithVirtualName(2),
      "m5ad.12xlarge" : enumeratedBlockDevicesWithVirtualName(2),
      "m5ad.24xlarge" : enumeratedBlockDevicesWithVirtualName(4),

      "m6g.medium"   : sizedBlockDevicesForEbs(40),
      "m6g.large"    : sizedBlockDevicesForEbs(40),
      "m6g.xlarge"   : sizedBlockDevicesForEbs(80),
      "m6g.2xlarge"  : sizedBlockDevicesForEbs(80),
      "m6g.4xlarge"  : sizedBlockDevicesForEbs(120),
      "m6g.8xlarge"  : sizedBlockDevicesForEbs(120),
      "m6g.12xlarge" : sizedBlockDevicesForEbs(120),
      "m6g.16xlarge" : sizedBlockDevicesForEbs(120),
      "m6g.metal"    : sizedBlockDevicesForEbs(120),

      "m6gd.medium"   : enumeratedBlockDevicesWithVirtualName(1),
      "m6gd.large"    : enumeratedBlockDevicesWithVirtualName(1),
      "m6gd.xlarge"   : enumeratedBlockDevicesWithVirtualName(1),
      "m6gd.2xlarge"  : enumeratedBlockDevicesWithVirtualName(1),
      "m6gd.4xlarge"  : enumeratedBlockDevicesWithVirtualName(1),
      "m6gd.8xlarge"  : enumeratedBlockDevicesWithVirtualName(1),
      "m6gd.12xlarge" : enumeratedBlockDevicesWithVirtualName(2),
      "m6gd.16xlarge" : enumeratedBlockDevicesWithVirtualName(2),
      "m6gd.metal"    : enumeratedBlockDevicesWithVirtualName(2),

      "r3.large"    : enumeratedBlockDevicesWithVirtualName(1),
      "r3.xlarge"   : enumeratedBlockDevicesWithVirtualName(1),
      "r3.2xlarge"  : enumeratedBlockDevicesWithVirtualName(1),
      "r3.4xlarge"  : enumeratedBlockDevicesWithVirtualName(1),
      "r3.8xlarge"  : enumeratedBlockDevicesWithVirtualName(2),

      "r4.large"    : sizedBlockDevicesForEbs(40),
      "r4.xlarge"   : sizedBlockDevicesForEbs(80),
      "r4.2xlarge"  : sizedBlockDevicesForEbs(80),
      "r4.4xlarge"  : sizedBlockDevicesForEbs(120),
      "r4.8xlarge"  : sizedBlockDevicesForEbs(120),
      "r4.16xlarge" : sizedBlockDevicesForEbs(120),

      "r5.large"    : sizedBlockDevicesForEbs(40),
      "r5.xlarge"   : sizedBlockDevicesForEbs(80),
      "r5.2xlarge"  : sizedBlockDevicesForEbs(80),
      "r5.4xlarge"  : sizedBlockDevicesForEbs(120),
      "r5.8xlarge"  : sizedBlockDevicesForEbs(120),
      "r5.12xlarge" : sizedBlockDevicesForEbs(120),
      "r5.16xlarge" : sizedBlockDevicesForEbs(120),
      "r5.24xlarge" : sizedBlockDevicesForEbs(120),

      "r5d.large"    : enumeratedBlockDevicesWithVirtualName(1),
      "r5d.xlarge"   : enumeratedBlockDevicesWithVirtualName(1),
      "r5d.2xlarge"  : enumeratedBlockDevicesWithVirtualName(1),
      "r5d.4xlarge"  : enumeratedBlockDevicesWithVirtualName(2),
      "r5d.8xlarge"  : enumeratedBlockDevicesWithVirtualName(2),
      "r5d.12xlarge" : enumeratedBlockDevicesWithVirtualName(2),
      "r5d.16xlarge" : enumeratedBlockDevicesWithVirtualName(4),
      "r5d.24xlarge" : enumeratedBlockDevicesWithVirtualName(4),

      "r5n.large"    : sizedBlockDevicesForEbs(40),
      "r5n.xlarge"   : sizedBlockDevicesForEbs(80),
      "r5n.2xlarge"  : sizedBlockDevicesForEbs(80),
      "r5n.4xlarge"  : sizedBlockDevicesForEbs(120),
      "r5n.8xlarge"  : sizedBlockDevicesForEbs(120),
      "r5n.12xlarge" : sizedBlockDevicesForEbs(120),
      "r5n.16xlarge" : sizedBlockDevicesForEbs(120),
      "r5n.24xlarge" : sizedBlockDevicesForEbs(120),

      "r5dn.large"    : enumeratedBlockDevicesWithVirtualName(1),
      "r5dn.xlarge"   : enumeratedBlockDevicesWithVirtualName(1),
      "r5dn.2xlarge"  : enumeratedBlockDevicesWithVirtualName(1),
      "r5dn.4xlarge"  : enumeratedBlockDevicesWithVirtualName(2),
      "r5dn.8xlarge"  : enumeratedBlockDevicesWithVirtualName(2),
      "r5dn.12xlarge" : enumeratedBlockDevicesWithVirtualName(2),
      "r5dn.16xlarge" : enumeratedBlockDevicesWithVirtualName(4),
      "r5dn.24xlarge" : enumeratedBlockDevicesWithVirtualName(4),

      "r5a.large"    : sizedBlockDevicesForEbs(40),
      "r5a.xlarge"   : sizedBlockDevicesForEbs(80),
      "r5a.2xlarge"  : sizedBlockDevicesForEbs(80),
      "r5a.4xlarge"  : sizedBlockDevicesForEbs(120),
      "r5a.8xlarge"  : sizedBlockDevicesForEbs(120),
      "r5a.12xlarge" : sizedBlockDevicesForEbs(120),
      "r5a.16xlarge" : sizedBlockDevicesForEbs(120),
      "r5a.24xlarge" : sizedBlockDevicesForEbs(120),

      "r5ad.large"    : enumeratedBlockDevicesWithVirtualName(1),
      "r5ad.xlarge"   : enumeratedBlockDevicesWithVirtualName(1),
      "r5ad.2xlarge"  : enumeratedBlockDevicesWithVirtualName(1),
      "r5ad.4xlarge"  : enumeratedBlockDevicesWithVirtualName(2),
      "r5ad.12xlarge" : enumeratedBlockDevicesWithVirtualName(2),
      "r5ad.24xlarge" : enumeratedBlockDevicesWithVirtualName(4),

      "r6g.medium"   : sizedBlockDevicesForEbs(40),
      "r6g.large"    : sizedBlockDevicesForEbs(40),
      "r6g.xlarge"   : sizedBlockDevicesForEbs(80),
      "r6g.2xlarge"  : sizedBlockDevicesForEbs(80),
      "r6g.4xlarge"  : sizedBlockDevicesForEbs(120),
      "r6g.8xlarge"  : sizedBlockDevicesForEbs(120),
      "r6g.12xlarge" : sizedBlockDevicesForEbs(120),
      "r6g.16xlarge" : sizedBlockDevicesForEbs(120),
      "r6g.metal"    : sizedBlockDevicesForEbs(120),

      "r6gd.medium"   : enumeratedBlockDevicesWithVirtualName(1),
      "r6gd.large"    : enumeratedBlockDevicesWithVirtualName(1),
      "r6gd.xlarge"   : enumeratedBlockDevicesWithVirtualName(1),
      "r6gd.2xlarge"  : enumeratedBlockDevicesWithVirtualName(1),
      "r6gd.4xlarge"  : enumeratedBlockDevicesWithVirtualName(1),
      "r6gd.8xlarge"  : enumeratedBlockDevicesWithVirtualName(1),
      "r6gd.12xlarge" : enumeratedBlockDevicesWithVirtualName(2),
      "r6gd.16xlarge" : enumeratedBlockDevicesWithVirtualName(2),
      "r6gd.metal"    : enumeratedBlockDevicesWithVirtualName(2),

      "p2.xlarge"   : sizedBlockDevicesForEbs(80),
      "p2.8xlarge"  : sizedBlockDevicesForEbs(120),
      "p2.16xlarge" : sizedBlockDevicesForEbs(120),

      "p3.2xlarge"  : sizedBlockDevicesForEbs(80),
      "p3.8xlarge"  : sizedBlockDevicesForEbs(120),
      "p3.16xlarge" : sizedBlockDevicesForEbs(120),

      "p3dn.24xlarge" : sizedBlockDevicesForEbs(120),

      "t1.micro"    : [],

      "t2.nano"     : [],
      "t2.micro"    : [],
      "t2.small"    : [],
      "t2.medium"   : [],
      "t2.large"    : [],
      "t2.xlarge"   : [],
      "t2.2xlarge"  : [],

      "t3.nano"     : [],
      "t3.micro"    : [],
      "t3.small"    : [],
      "t3.medium"   : [],
      "t3.large"    : [],
      "t3.xlarge"   : [],
      "t3.2xlarge"  : [],

      "t3a.nano"    : [],
      "t3a.micro"   : [],
      "t3a.small"   : [],
      "t3a.medium"  : [],
      "t3a.large"   : [],
      "t3a.xlarge"  : [],
      "t3a.2xlarge" : [],

      "x1.16xlarge" : enumeratedBlockDevicesWithVirtualName(1),
      "x1.32xlarge" : enumeratedBlockDevicesWithVirtualName(2),

      "x1e.xlarge"  : enumeratedBlockDevicesWithVirtualName(1),
      "x1e.2xlarge" : enumeratedBlockDevicesWithVirtualName(1),
      "x1e.4xlarge" : enumeratedBlockDevicesWithVirtualName(1),
      "x1e.8xlarge" : enumeratedBlockDevicesWithVirtualName(1),
      "x1e.16xlarge": enumeratedBlockDevicesWithVirtualName(1),
      "x1e.32xlarge": enumeratedBlockDevicesWithVirtualName(2),

      "z1d.large"    : enumeratedBlockDevicesWithVirtualName(1),
      "z1d.xlarge"   : enumeratedBlockDevicesWithVirtualName(1),
      "z1d.2xlarge"  : enumeratedBlockDevicesWithVirtualName(1),
      "z1d.3xlarge"  : enumeratedBlockDevicesWithVirtualName(1),
      "z1d.6xlarge"  : enumeratedBlockDevicesWithVirtualName(1),
      "z1d.12xlarge" : enumeratedBlockDevicesWithVirtualName(2),

    ].asImmutable()
  }

  List<AmazonBlockDevice> enumeratedBlockDevicesWithVirtualName(int size) {
    def letters = ('a'..'z').collect { it }
    (0..<size).collect {
      def letter = letters[it + 1]
      new AmazonBlockDevice(deviceName: "/dev/sd${letter}", virtualName: "ephemeral${it}")
    }
  }

  def defaultBlockDevicesForEbsOnly() {
    [
      new AmazonBlockDevice(deviceName: "/dev/sdb", size: 125, volumeType: deployDefaults.defaultBlockDeviceType),
      new AmazonBlockDevice(deviceName: "/dev/sdc", size: 125, volumeType: deployDefaults.defaultBlockDeviceType),
    ]
  }

  def sizedBlockDevicesForEbs(int capacity) {
    [
      new AmazonBlockDevice(deviceName: "/dev/sdb", size: capacity, volumeType: deployDefaults.defaultBlockDeviceType)
    ]
  }

  List<AmazonBlockDevice> getBlockDevicesForInstanceType(String instanceType) {
    def blockDevices = blockDevicesByInstanceType[instanceType]
    if (blockDevices == null && deployDefaults.unknownInstanceTypeBlockDevice) {
      // return a default block device mapping if no instance-specific default exists <optional>
      return [deployDefaults.unknownInstanceTypeBlockDevice]
    }

    return blockDevices
  }

  Set<String> getInstanceTypesWithBlockDeviceMappings() {
    return blockDevicesByInstanceType.keySet()
  }
}
