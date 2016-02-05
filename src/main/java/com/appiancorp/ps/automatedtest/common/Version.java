package com.appiancorp.ps.automatedtest.common;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

/**
 * Version is a helper class used for parsing and comparing of version strings and numbers.
 * 
 * @author Michael Chirlin
 * @version 1.0
 * @since 2014-10-07
 */
public class Version implements Comparable<Version> {
  public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd MMM yyyy");
  private static final String SEPARATOR = "._-";

  public static final Version NULL_VERSION = new Version(0, 0, 0, 0, "");

  private Integer a;
  private Integer b;
  private Integer c;
  private Integer d;
  private String actual;

  public Version(String version) {
    Version v = Version.parse(version, null);
    this.a = v.a;
    this.b = v.b;
    this.c = v.c;
    this.d = v.d;
  }

  public Version(Integer a) {
    this(a, null, null, null, null);
  }

  public Version(Integer a, Integer b) {
    this(a, b, null, null, null);
  }

  public Version(Integer a, Integer b, Integer c) {
    this(a, b, c, null, null);
  }

  public Version(Integer a, Integer b, Integer c, Integer d) {
    this(a, b, c, d, null);
  }

  public Version(Integer a, Integer b, Integer c, Integer d, String actual) {
    this.a = a;
    this.b = b;
    this.c = c;
    this.d = d;
    this.actual = actual;
  }

  /**
   * Returns a Version object from a string of format x.x.x.x
   * 
   * @param version
   *          string of version to parse in x.x.x.x format
   * @param actual
   *          string of actual - used for hotfix versioning
   * @return Version object
   */
  public static Version parse(String version, String actual) {

    Integer a = null;
    Integer b = null;
    Integer c = null;
    Integer d = null;

    if (version == null) {
      return NULL_VERSION;
    }

    version = version.trim();
    if (version.length() == 0) {
      return NULL_VERSION;
    }

    try {
      StringTokenizer st = new StringTokenizer(version, SEPARATOR, false);
      a = new Integer(st.nextToken());

      if (st.hasMoreTokens()) {
        b = new Integer(st.nextToken());

        if (st.hasMoreTokens()) {
          c = new Integer(st.nextToken());

          if (st.hasMoreTokens()) {
            d = new Integer(st.nextToken());
          }
        }
      }
    } catch (NoSuchElementException e) {
      IllegalArgumentException iae = new IllegalArgumentException("Invalid version \"" + version + "\": invalid format");
      iae.initCause(e);
      throw iae;
    } catch (NumberFormatException ne) {
      return new Version(a, b, c, d, actual);
    }

    return new Version(a, b, c, d, actual);
  }

  public String getActual() {
    return actual;
  }

  public String toNumber() {
    return String.format("%d%d%d%d", a, (b == null) ? 0 : b, (c == null) ? 0 : c, (d == null) ? 0 : d);
  }

  @Override
  public String toString() {
    if (d != null) {
      return String.format("%d.%d.%d.%d", a, b, c, d);
    } else if (c != null) {
      return String.format("%d.%d.%d", a, b, c);
    } else if (b != null) {
      return String.format("%d.%d", a, b);
    } else {
      return String.format("%d", a);
    }
  }

  /**
   * Compares this version to the specified version and returns a
   * "match quality" that indicates how closely they match. The match is based
   * on a 4 part version (a.b.c.d) and a actual hash.
   * 0: (WORST) No parts of the version matches
   * 1: Only the major version matches (a.x.x.x)
   * 2: The major and minor versions match (a.b.x.x)
   * 3: The first 3 parts of the version match (a.b.c.x)
   * 4: All 4 parts of the version match (a.b.c.d)
   * 5: (BEST) All 4 parts of the version match (a.b.c.d) and the actual hash matches
   */
  public int match(Version version) {
    if (version == null)
      return 0;
    if (a.equals(version.a)) {
      if (b == null || version.b == null || b.equals(version.b)) {
        if (c == null || version.c == null || c.equals(version.c)) {
          if (d == null || version.d == null || d.equals(version.d)) {
            if (actual == null || actual.equals(version.actual)) {
              return 5;
            }
            return 4;
          }
          return 3;
        }
        return 2;
      }
      return 1;
    }
    return 0;
  }

  /**
   * Returns the index of the best match in a list
   * 
   * @param version
   *          the version to match to
   * @param versions
   *          the list of versions to match against
   * @return
   */
  public static Integer getBestIndexFromList(Version version, List<Version> versions) {
    // Create a copy of the list and sort it
    List<Version> vs = new ArrayList<Version>(versions);

    // Sorts Newest to Oldest (i.e 7.9 - 6.7)
    Collections.sort(vs);
    Collections.reverse(vs);

    // Default to the version index to the last in the sorted list (in case no match was found)
    int index = vs.size() - 1;

    for (int i = 0; i < vs.size(); i++) {
      int compare = vs.get(i).compareTo(version);

      // Find the first index that is equal to or less than the input version
      if (compare <= 0) {
        index = i;
        break;
      }
    }

    // Lookup the index from the input versions list as it may not have the same order as the sorted list
    return versions.indexOf(vs.get(index));
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((a == null) ? 0 : a.hashCode());
    result = prime * result + ((b == null) ? 0 : b.hashCode());
    result = prime * result + ((c == null) ? 0 : c.hashCode());
    result = prime * result + ((d == null) ? 0 : d.hashCode());
    result = prime * result + ((actual == null) ? 0 : actual.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Version other = (Version) obj;
    if (a == null) {
      if (other.a != null)
        return false;
    } else if (!a.equals(other.a))
      return false;
    if (b == null) {
      if (other.b != null)
        return false;
    } else if (!b.equals(other.b))
      return false;
    if (c == null) {
      if (other.c != null)
        return false;
    } else if (!c.equals(other.c))
      return false;
    if (d == null) {
      if (other.d != null)
        return false;
    } else if (!d.equals(other.d))
      return false;
    if (actual == null) {
      if (other.actual != null)
        return false;
    } else if (!actual.equals(other.actual))
      return false;
    return true;
  }

  @Override
  public int compareTo(Version o) {
    if (this.equals(o)) {
      return 0;
    }
    int compareValue = 0;
    compareValue = this.a.compareTo(o.a);
    if (compareValue == 0 && this.b != null && o.b != null) {
      compareValue = this.b.compareTo(o.b);
    }
    if (compareValue == 0 && this.c != null && o.c != null) {
      compareValue = this.c.compareTo(o.c);
    }
    if (compareValue == 0 && this.d != null && o.d != null) {
      compareValue = this.d.compareTo(o.d);
    }
    // Revisions are simply github hashes.
    // Versions with the same versions but different revsions will
    // return false for the equals method and 0 for the compareTo method.
    return compareValue;
  }
}
