/*
 *  Copyright (c) 2023 Maxime Wiewiora
 *
 *  Use of this source code is governed by an MIT-style
 *  license that can be found in the LICENSE file or at
 *  https://opensource.org/licenses/MIT.
 */

package com.github.maximevw.moka.enums;

import lombok.Getter;

import java.util.Arrays;

/**
 * The Ganache version level.
 */
public enum GanacheVersionLevel {

	/**
	 * Ganache CLI versions 6.x.
	 */
    V6("trufflesuite/ganache-cli", 6),
	/**
	 * Ganache CLI versions 7.x.
	 */
    V7("trufflesuite/ganache", 7);

    private final String imageName;
    @Getter
    private final int majorVersion;

    GanacheVersionLevel(final String imageName, final int majorVersion) {
        this.imageName = imageName;
        this.majorVersion = majorVersion;
    }

	/**
	 * Determines the version level based on the Ganache image name.
	 *
	 * @param imageName The Docker image name.
	 * @return The version level. By default, if the name is not recognized, it will be considered as {@link #V7}.
	 */
    public static GanacheVersionLevel fromImageName(final String imageName) {
        return Arrays.stream(values()).filter(value -> value.imageName.equals(imageName)).findFirst().orElse(V7);
    }
}
