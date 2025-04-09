/*
 *    This file is part of CasADi.
 *
 *    CasADi -- A symbolic framework for dynamic optimization.
 *    Copyright (C) 2010-2023 Joel Andersson, Joris Gillis, Moritz Diehl,
 *                            KU Leuven. All rights reserved.
 *    Copyright (C) 2011-2014 Greg Horn
 *
 *    CasADi is free software; you can redistribute it and/or
 *    modify it under the terms of the GNU Lesser General Public
 *    License as published by the Free Software Foundation; either
 *    version 3 of the License, or (at your option) any later version.
 *
 *    CasADi is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *    Lesser General Public License for more details.
 *
 *    You should have received a copy of the GNU Lesser General Public
 *    License along with CasADi; if not, write to the Free Software
 *    Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 *
 */
#ifndef CASADI_CONFIG_H // NOLINT(build/header_guard)
#define CASADI_CONFIG_H // NOLINT(build/header_guard)

#define CASADI_MAJOR_VERSION 3
#define CASADI_MINOR_VERSION 7
#define CASADI_PATCH_VERSION 0
#define CASADI_IS_RELEASE 1
#define CASADI_VERSION_STRING "3.7.0"
#define CASADI_GIT_REVISION "1b2553ec3013ab9a551e7c12ed994cc04b5d264a"  // NOLINT(whitespace/line_length)
#define CASADI_GIT_DESCRIBE "3.6.3-976.1b2553ec3"  // NOLINT(whitespace/line_length)
#define CASADI_FEATURE_LIST "\n * dynamic-loading, Support for import of FMI 3.0 binaries\n * ipopt-sourcebuild, Build IPOPT (BUILD_IPOPT_VERSION=3.14.11.mod) from downloaded source (BUILD_IPOPT_GIT_REPO=https://github.com/jgillis/Ipopt-1.git).\n * mumps-sourcebuild, Build MUMPS (BUILD_MUMPS_TP_VERSION=releases/3.0.2) from downloaded source (BUILD_MUMPS_TP_GIT_REPO=https://github.com/coin-or-tools/ThirdParty-Mumps.git).\n * metis-sourcebuild, Build METIS (BUILD_METIS_TP_VERSION=6997f64) from downloaded source.\n * lapack-interface, Interface to LAPACK.\n * mumps-interface, Interface to MUMPS.\n * ipopt-interface, Interface to the NLP solver Ipopt.\n"  // NOLINT(whitespace/line_length)
#define CASADI_BUILD_TYPE "Release"  // NOLINT(whitespace/line_length)
#define CASADI_COMPILER_ID "GNU"  // NOLINT(whitespace/line_length)
#define CASADI_COMPILER "c++"  // NOLINT(whitespace/line_length)
#define CASADI_COMPILER_FLAGS " -fPIC    -DUSE_CXX11 -DHAVE_MKSTEMPS -DWITH_DEEPBIND -DCASADI_MAJOR_VERSION=3 -DCASADI_MINOR_VERSION=7 -DCASADI_PATCH_VERSION=0 -DCASADI_IS_RELEASE=1 -DCASADI_VERSION=31 -D_USE_MATH_DEFINES -D_SCL_SECURE_NO_WARNINGS -DWITH_DL -DWITH_DEPRECATED_FEATURES -DCASADI_DEFAULT_COMPILER_PLUGIN=shell -DCASADI_SNPRINTF=snprintf"  // NOLINT(whitespace/line_length)
#define CASADI_MODULES "casadi;casadi_linsol_lapacklu;casadi_linsol_lapackqr;casadi_nlpsol_ipopt;casadi_linsol_mumps;casadi_conic_nlpsol;casadi_conic_qrqp;casadi_conic_ipqp;casadi_nlpsol_qrsqp;casadi_importer_shell;casadi_integrator_rk;casadi_integrator_collocation;casadi_interpolant_linear;casadi_interpolant_bspline;casadi_linsol_symbolicqr;casadi_linsol_qr;casadi_linsol_ldl;casadi_linsol_tridiag;casadi_linsol_lsqr;casadi_nlpsol_sqpmethod;casadi_nlpsol_feasiblesqpmethod;casadi_nlpsol_scpgen;casadi_rootfinder_newton;casadi_rootfinder_fast_newton;casadi_rootfinder_nlpsol"  // NOLINT(whitespace/line_length)
#define CASADI_PLUGINS "Linsol::lapacklu;Linsol::lapackqr;Nlpsol::ipopt;Linsol::mumps;Conic::nlpsol;Conic::qrqp;Conic::ipqp;Nlpsol::qrsqp;Importer::shell;Integrator::rk;Integrator::collocation;Interpolant::linear;Interpolant::bspline;Linsol::symbolicqr;Linsol::qr;Linsol::ldl;Linsol::tridiag;Linsol::lsqr;Nlpsol::sqpmethod;Nlpsol::feasiblesqpmethod;Nlpsol::scpgen;Rootfinder::newton;Rootfinder::fast_newton;Rootfinder::nlpsol"  // NOLINT(whitespace/line_length)
#define CASADI_INSTALL_PREFIX ""  // NOLINT(whitespace/line_length)
#define CASADI_SHARED_LIBRARY_PREFIX "lib"  // NOLINT(whitespace/line_length)
#define CASADI_SHARED_LIBRARY_SUFFIX ".so"  // NOLINT(whitespace/line_length)
#define CASADI_OBJECT_FILE_SUFFIX ".o"  // NOLINT(whitespace/line_length)

#endif  // CASADI_CONFIG_H // NOLINT(build/header_guard)
