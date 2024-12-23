USE [master]
GO
/****** Object:  Database [DATN_CHINSHOES]    Script Date: 23/11/2024 1:15:34 PM ******/
CREATE DATABASE [DATN_CHINSHOES]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'DATN_CHINSHOES', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL16.SQLEXPRESS\MSSQL\DATA\DATN_CHINSHOES.mdf' , SIZE = 8192KB , MAXSIZE = UNLIMITED, FILEGROWTH = 65536KB )
 LOG ON 
( NAME = N'DATN_CHINSHOES_log', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL16.SQLEXPRESS\MSSQL\DATA\DATN_CHINSHOES_log.ldf' , SIZE = 8192KB , MAXSIZE = 2048GB , FILEGROWTH = 65536KB )
 WITH CATALOG_COLLATION = DATABASE_DEFAULT, LEDGER = OFF
GO
ALTER DATABASE [DATN_CHINSHOES] SET COMPATIBILITY_LEVEL = 160
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [DATN_CHINSHOES].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [DATN_CHINSHOES] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [DATN_CHINSHOES] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [DATN_CHINSHOES] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [DATN_CHINSHOES] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [DATN_CHINSHOES] SET ARITHABORT OFF 
GO
ALTER DATABASE [DATN_CHINSHOES] SET AUTO_CLOSE ON 
GO
ALTER DATABASE [DATN_CHINSHOES] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [DATN_CHINSHOES] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [DATN_CHINSHOES] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [DATN_CHINSHOES] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [DATN_CHINSHOES] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [DATN_CHINSHOES] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [DATN_CHINSHOES] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [DATN_CHINSHOES] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [DATN_CHINSHOES] SET  ENABLE_BROKER 
GO
ALTER DATABASE [DATN_CHINSHOES] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [DATN_CHINSHOES] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [DATN_CHINSHOES] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [DATN_CHINSHOES] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [DATN_CHINSHOES] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [DATN_CHINSHOES] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [DATN_CHINSHOES] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [DATN_CHINSHOES] SET RECOVERY SIMPLE 
GO
ALTER DATABASE [DATN_CHINSHOES] SET  MULTI_USER 
GO
ALTER DATABASE [DATN_CHINSHOES] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [DATN_CHINSHOES] SET DB_CHAINING OFF 
GO
ALTER DATABASE [DATN_CHINSHOES] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [DATN_CHINSHOES] SET TARGET_RECOVERY_TIME = 60 SECONDS 
GO
ALTER DATABASE [DATN_CHINSHOES] SET DELAYED_DURABILITY = DISABLED 
GO
ALTER DATABASE [DATN_CHINSHOES] SET ACCELERATED_DATABASE_RECOVERY = OFF  
GO
ALTER DATABASE [DATN_CHINSHOES] SET QUERY_STORE = ON
GO
ALTER DATABASE [DATN_CHINSHOES] SET QUERY_STORE (OPERATION_MODE = READ_WRITE, CLEANUP_POLICY = (STALE_QUERY_THRESHOLD_DAYS = 30), DATA_FLUSH_INTERVAL_SECONDS = 900, INTERVAL_LENGTH_MINUTES = 60, MAX_STORAGE_SIZE_MB = 1000, QUERY_CAPTURE_MODE = AUTO, SIZE_BASED_CLEANUP_MODE = AUTO, MAX_PLANS_PER_QUERY = 200, WAIT_STATS_CAPTURE_MODE = ON)
GO
USE [DATN_CHINSHOES]
GO
/****** Object:  Table [dbo].[chat_lieu]    Script Date: 23/11/2024 1:15:34 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[chat_lieu](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[ma] [nvarchar](100) NULL,
	[trang_thai] [nvarchar](100) NULL,
	[ten] [nvarchar](100) NULL,
	[ngay_tao] [datetime] NULL,
	[nguoi_tao] [nvarchar](100) NULL,
	[ngay_sua] [datetime] NULL,
	[nguoi_sua] [nvarchar](100) NULL,
	[deleted] [bit] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[co_giay]    Script Date: 23/11/2024 1:15:34 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[co_giay](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[ma] [nvarchar](100) NULL,
	[trang_thai] [nvarchar](100) NULL,
	[ten] [nvarchar](100) NULL,
	[ngay_tao] [datetime] NULL,
	[nguoi_tao] [nvarchar](100) NULL,
	[ngay_sua] [datetime] NULL,
	[nguoi_sua] [nvarchar](100) NULL,
	[deleted] [bit] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[danh_muc]    Script Date: 23/11/2024 1:15:34 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[danh_muc](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[ma] [nvarchar](100) NULL,
	[trang_thai] [nvarchar](100) NULL,
	[ten] [nvarchar](100) NULL,
	[ngay_tao] [datetime] NULL,
	[nguoi_tao] [nvarchar](100) NULL,
	[ngay_sua] [datetime] NULL,
	[nguoi_sua] [nvarchar](100) NULL,
	[deleted] [bit] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[de_giay]    Script Date: 23/11/2024 1:15:34 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[de_giay](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[ma] [nvarchar](100) NULL,
	[trang_thai] [nvarchar](100) NULL,
	[ten] [nvarchar](100) NULL,
	[ngay_tao] [datetime] NULL,
	[nguoi_tao] [nvarchar](100) NULL,
	[ngay_sua] [datetime] NULL,
	[nguoi_sua] [nvarchar](100) NULL,
	[deleted] [bit] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[dia_chi]    Script Date: 23/11/2024 1:15:34 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[dia_chi](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[id_khach_hang] [int] NULL,
	[tinh_thanh_pho] [nvarchar](100) NULL,
	[quan_huyen] [nvarchar](100) NULL,
	[phuong_xa] [nvarchar](100) NULL,
	[dia_chi_cu_the] [nvarchar](255) NULL,
	[mac_dinh] [bit] NULL,
	[ghi_chu] [nvarchar](256) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[gio_hang]    Script Date: 23/11/2024 1:15:34 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[gio_hang](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[id_khach_hang] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[gio_hang_chi_tiet]    Script Date: 23/11/2024 1:15:34 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[gio_hang_chi_tiet](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[id_san_pham_chi_tiet] [int] NULL,
	[id_gio_hang] [int] NULL,
	[so_luong] [int] NULL,
	[don_gia] [float] NULL,
	[ngay_tao] [datetime] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[hinh_anh]    Script Date: 23/11/2024 1:15:34 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[hinh_anh](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[id_san_pham_chi_tiet] [int] NULL,
	[duong_dan] [nvarchar](255) NULL,
	[trang_thai] [nvarchar](100) NULL,
	[loai] [nvarchar](20) NULL,
	[ngay_tao] [datetime] NULL,
	[nguoi_tao] [nvarchar](100) NULL,
	[ngay_sua] [datetime] NULL,
	[nguoi_sua] [nvarchar](100) NULL,
	[deleted] [bit] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[hinh_thuc_thanh_toan]    Script Date: 23/11/2024 1:15:34 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[hinh_thuc_thanh_toan](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[ten] [nvarchar](100) NULL,
	[loaigiaodich] [nvarchar](100) NULL,
	[so_tien_thanh_toan] [float] NULL,
	[trang_thai] [nvarchar](100) NULL,
	[ngay_tao] [datetime] NULL,
	[nguoi_tao] [nvarchar](100) NULL,
	[ngay_sua] [datetime] NULL,
	[nguoi_sua] [nvarchar](100) NULL,
	[deleted] [bit] NULL,
	[vnpOrderInfo] [varchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[hinh_thuc_thanh_toan_hoa_don]    Script Date: 23/11/2024 1:15:34 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[hinh_thuc_thanh_toan_hoa_don](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[id_hinh_thuc_thanh_toan] [int] NULL,
	[id_hoa_don] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[hoa_don]    Script Date: 23/11/2024 1:15:34 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[hoa_don](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[id_nhan_vien] [int] NULL,
	[id_phieu_giam_gia] [int] NULL,
	[id_khach_hang] [int] NULL,
	[ma] [nvarchar](100) NULL,
	[tong_tien] [float] NULL,
	[ten_nguoi_nhan] [nvarchar](100) NULL,
	[so_dien_thoai] [nvarchar](100) NULL,
	[tinh_thanh_pho] [nvarchar](100) NULL,
	[quan_huyen] [nvarchar](100) NULL,
	[phuong_xa] [nvarchar](100) NULL,
	[dia_chi_cu_the] [nvarchar](100) NULL,
	[phi_ship] [float] NULL,
	[thoi_gian_nhan_du_kien] [datetime] NULL,
	[loai_hoa_don] [bit] NULL,
	[ghi_chu] [nvarchar](100) NULL,
	[ngay_tao] [datetime] NULL,
	[nguoi_tao] [nvarchar](100) NULL,
	[ngay_sua] [datetime] NULL,
	[nguoi_sua] [nvarchar](100) NULL,
	[trang_thai] [nvarchar](100) NULL,
	[qr_image] [varchar](200) NULL,
	[deleted] [bit] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[hoa_don_chi_tiet]    Script Date: 23/11/2024 1:15:34 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[hoa_don_chi_tiet](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[id_hoa_don] [int] NULL,
	[id_san_pham_chi_tiet] [int] NULL,
	[ma] [nvarchar](100) NULL,
	[so_luong] [int] NULL,
	[don_gia] [float] NULL,
	[ngay_tao] [datetime] NULL,
	[nguoi_tao] [nvarchar](100) NULL,
	[ngay_sua] [datetime] NULL,
	[nguoi_sua] [nvarchar](100) NULL,
	[deleted] [bit] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[khach_hang]    Script Date: 23/11/2024 1:15:34 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[khach_hang](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[ma] [nvarchar](100) NULL,
	[ten] [nvarchar](100) NULL,
	[mat_khau] [varchar](100) NULL,
	[email] [varchar](100) NULL,
	[so_dien_thoai] [varchar](100) NULL,
	[ngay_sinh] [date] NULL,
	[gioi_tinh] [bit] NULL,
	[trang_thai] [nvarchar](100) NULL,
	[vai_tro] [nvarchar](100) NULL,
	[ngay_tao] [datetime] NULL,
	[nguoi_tao] [nvarchar](100) NULL,
	[ngay_sua] [datetime] NULL,
	[nguoi_sua] [nvarchar](100) NULL,
	[hinh_anh] [varchar](255) NULL,
	[deleted] [bit] NULL,
	[otpQuenMatKhau] [varchar](10) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[khach_hang_phieu_giam_gia]    Script Date: 23/11/2024 1:15:34 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[khach_hang_phieu_giam_gia](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[id_khach_hang] [int] NULL,
	[id_phieu_giam_gia] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[lich_su_hoa_don]    Script Date: 23/11/2024 1:15:34 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[lich_su_hoa_don](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[id_khach_hang] [int] NULL,
	[id_nhan_vien] [int] NULL,
	[id_hoa_don] [int] NULL,
	[hanh_dong] [nvarchar](100) NULL,
	[ngay_tao] [datetime] NULL,
	[nguoi_tao] [nvarchar](100) NULL,
	[ngay_sua] [datetime] NULL,
	[nguoi_sua] [nvarchar](100) NULL,
	[deleted] [bit] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[mau_sac]    Script Date: 23/11/2024 1:15:34 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[mau_sac](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[ma] [nvarchar](100) NULL,
	[trang_thai] [nvarchar](100) NULL,
	[ten] [nvarchar](100) NULL,
	[ngay_tao] [datetime] NULL,
	[nguoi_tao] [nvarchar](100) NULL,
	[ngay_sua] [datetime] NULL,
	[nguoi_sua] [nvarchar](100) NULL,
	[deleted] [bit] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[nha_san_xuat]    Script Date: 23/11/2024 1:15:34 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[nha_san_xuat](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[ma] [nvarchar](100) NULL,
	[trang_thai] [nvarchar](100) NULL,
	[ten] [nvarchar](100) NULL,
	[ngay_tao] [datetime] NULL,
	[nguoi_tao] [nvarchar](100) NULL,
	[ngay_sua] [datetime] NULL,
	[nguoi_sua] [nvarchar](100) NULL,
	[deleted] [bit] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[nhan_vien]    Script Date: 23/11/2024 1:15:34 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[nhan_vien](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[ma] [nvarchar](100) NULL,
	[ten] [nvarchar](100) NULL,
	[mat_khau] [varchar](100) NULL,
	[hinh_anh] [varchar](255) NULL,
	[email] [varchar](100) NULL,
	[so_dien_thoai] [varchar](100) NULL,
	[ngay_sinh] [date] NULL,
	[tinh_thanh_pho] [nvarchar](100) NULL,
	[quan_huyen] [nvarchar](100) NULL,
	[phuong_xa] [nvarchar](100) NULL,
	[dia_chi_cu_the] [nvarchar](100) NULL,
	[gioi_tinh] [bit] NULL,
	[trang_thai] [nvarchar](100) NULL,
	[vai_tro] [nvarchar](100) NULL,
	[ngay_tao] [datetime] NULL,
	[nguoi_tao] [nvarchar](100) NULL,
	[ngay_sua] [datetime] NULL,
	[nguoi_sua] [nvarchar](100) NULL,
	[deleted] [bit] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[phieu_giam_gia]    Script Date: 23/11/2024 1:15:34 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[phieu_giam_gia](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[ma] [nvarchar](100) NULL,
	[trang_thai] [nvarchar](100) NULL,
	[so_luong] [int] NULL,
	[ten] [nvarchar](100) NULL,
	[mo_ta] [nvarchar](100) NULL,
	[ngay_bat_dau] [datetime] NULL,
	[ngay_ket_thuc] [datetime] NULL,
	[hinh_thuc_giam] [bit] NULL,
	[gia_tri_don_toi_thieu] [float] NULL,
	[giam_toi_da] [float] NULL,
	[so_tien_giam] [float] NULL,
	[phan_tram_giam] [float] NULL,
	[hinh_thuc_su_dung] [bit] NULL,
	[ngay_tao] [datetime] NULL,
	[nguoi_tao] [nvarchar](100) NULL,
	[ngay_sua] [datetime] NULL,
	[nguoi_sua] [nvarchar](100) NULL,
	[deleted] [bit] NULL,
	[block] [bit] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[san_pham]    Script Date: 23/11/2024 1:15:34 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[san_pham](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[ma] [nvarchar](100) NULL,
	[trang_thai] [nvarchar](100) NULL,
	[id_co_giay] [int] NULL,
	[id_de_giay] [int] NULL,
	[id_danh_muc] [int] NULL,
	[id_thuong_hieu] [int] NULL,
	[ten] [nvarchar](100) NULL,
	[ghi_chu] [nvarchar](256) NULL,
	[ngay_tao] [datetime] NULL,
	[nguoi_tao] [nvarchar](100) NULL,
	[ngay_sua] [datetime] NULL,
	[nguoi_sua] [nvarchar](100) NULL,
	[deleted] [bit] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[san_pham_chi_tiet]    Script Date: 23/11/2024 1:15:34 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[san_pham_chi_tiet](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[id_san_pham] [int] NULL,
	[id_xuat_xu] [int] NULL,
	[id_nha_san_xuat] [int] NULL,
	[id_chat_lieu] [int] NULL,
	[id_size] [int] NULL,
	[id_mau_sac] [int] NULL,
	[ma] [nvarchar](100) NULL,
	[trang_thai] [nvarchar](100) NULL,
	[don_gia] [float] NULL,
	[mo_ta] [nvarchar](100) NULL,
	[so_luong] [int] NULL,
	[khoi_luong] [nvarchar](50) NULL,
	[don_vi_tinh] [nvarchar](50) NULL,
	[ghi_chu] [nvarchar](256) NULL,
	[ngay_tao] [datetime] NULL,
	[nguoi_tao] [nvarchar](100) NULL,
	[ngay_sua] [datetime] NULL,
	[nguoi_sua] [nvarchar](100) NULL,
	[qr_image] [varchar](200) NULL,
	[deleted] [bit] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[size]    Script Date: 23/11/2024 1:15:34 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[size](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[ma] [nvarchar](100) NULL,
	[trang_thai] [nvarchar](100) NULL,
	[ten] [nvarchar](100) NULL,
	[ngay_tao] [datetime] NULL,
	[nguoi_tao] [nvarchar](100) NULL,
	[ngay_sua] [datetime] NULL,
	[nguoi_sua] [nvarchar](100) NULL,
	[deleted] [bit] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[thuong_hieu]    Script Date: 23/11/2024 1:15:34 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[thuong_hieu](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[ma] [nvarchar](100) NULL,
	[trang_thai] [nvarchar](100) NULL,
	[ten] [nvarchar](100) NULL,
	[ngay_tao] [datetime] NULL,
	[nguoi_tao] [nvarchar](100) NULL,
	[ngay_sua] [datetime] NULL,
	[nguoi_sua] [nvarchar](100) NULL,
	[deleted] [bit] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[xuat_xu]    Script Date: 23/11/2024 1:15:34 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[xuat_xu](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[ma] [nvarchar](100) NULL,
	[trang_thai] [nvarchar](100) NULL,
	[ten] [nvarchar](100) NULL,
	[ngay_tao] [datetime] NULL,
	[nguoi_tao] [nvarchar](100) NULL,
	[ngay_sua] [datetime] NULL,
	[nguoi_sua] [nvarchar](100) NULL,
	[deleted] [bit] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
SET IDENTITY_INSERT [dbo].[chat_lieu] ON 

INSERT [dbo].[chat_lieu] ([id], [ma], [trang_thai], [ten], [ngay_tao], [nguoi_tao], [ngay_sua], [nguoi_sua], [deleted]) VALUES (1, N'CL001', N'Đang hoạt động', N'Da thật', CAST(N'2024-10-15T00:28:31.180' AS DateTime), N'Admin', NULL, NULL, 0)
INSERT [dbo].[chat_lieu] ([id], [ma], [trang_thai], [ten], [ngay_tao], [nguoi_tao], [ngay_sua], [nguoi_sua], [deleted]) VALUES (2, N'CL002', N'Đang hoạt động', N'Da PU', CAST(N'2024-10-15T00:28:31.180' AS DateTime), N'Admin', NULL, NULL, 0)
INSERT [dbo].[chat_lieu] ([id], [ma], [trang_thai], [ten], [ngay_tao], [nguoi_tao], [ngay_sua], [nguoi_sua], [deleted]) VALUES (3, N'CL003', N'Đang hoạt động', N'Vải Canvas', CAST(N'2024-10-15T00:28:31.180' AS DateTime), N'Admin', NULL, NULL, 0)
SET IDENTITY_INSERT [dbo].[chat_lieu] OFF
GO
SET IDENTITY_INSERT [dbo].[co_giay] ON 

INSERT [dbo].[co_giay] ([id], [ma], [trang_thai], [ten], [ngay_tao], [nguoi_tao], [ngay_sua], [nguoi_sua], [deleted]) VALUES (1, N'CG001', N'Đang hoạt động', N'Cổ thấp', CAST(N'2024-10-15T00:28:31.180' AS DateTime), N'Admin', NULL, NULL, 0)
INSERT [dbo].[co_giay] ([id], [ma], [trang_thai], [ten], [ngay_tao], [nguoi_tao], [ngay_sua], [nguoi_sua], [deleted]) VALUES (2, N'CG002', N'Đang hoạt động', N'Cổ cao', CAST(N'2024-10-15T00:28:31.180' AS DateTime), N'Admin', NULL, NULL, 0)
INSERT [dbo].[co_giay] ([id], [ma], [trang_thai], [ten], [ngay_tao], [nguoi_tao], [ngay_sua], [nguoi_sua], [deleted]) VALUES (3, N'CG003', N'Đang hoạt động', N'Cổ vừa', CAST(N'2024-10-15T00:28:31.180' AS DateTime), N'Admin', NULL, NULL, 0)
SET IDENTITY_INSERT [dbo].[co_giay] OFF
GO
SET IDENTITY_INSERT [dbo].[danh_muc] ON 

INSERT [dbo].[danh_muc] ([id], [ma], [trang_thai], [ten], [ngay_tao], [nguoi_tao], [ngay_sua], [nguoi_sua], [deleted]) VALUES (1, N'DM001', N'Đang hoạt động', N'Giày nam', CAST(N'2024-10-15T00:28:31.180' AS DateTime), N'Admin', NULL, NULL, 0)
INSERT [dbo].[danh_muc] ([id], [ma], [trang_thai], [ten], [ngay_tao], [nguoi_tao], [ngay_sua], [nguoi_sua], [deleted]) VALUES (2, N'DM002', N'Đang hoạt động', N'Giày nữ', CAST(N'2024-10-15T00:28:31.180' AS DateTime), N'Admin', NULL, NULL, 0)
INSERT [dbo].[danh_muc] ([id], [ma], [trang_thai], [ten], [ngay_tao], [nguoi_tao], [ngay_sua], [nguoi_sua], [deleted]) VALUES (3, N'DM003', N'Đang hoạt động', N'Giày trẻ em', CAST(N'2024-10-15T00:28:31.180' AS DateTime), N'Admin', NULL, NULL, 0)
SET IDENTITY_INSERT [dbo].[danh_muc] OFF
GO
SET IDENTITY_INSERT [dbo].[de_giay] ON 

INSERT [dbo].[de_giay] ([id], [ma], [trang_thai], [ten], [ngay_tao], [nguoi_tao], [ngay_sua], [nguoi_sua], [deleted]) VALUES (1, N'DG001', N'Đang hoạt động', N'Đế cao su', CAST(N'2024-10-15T00:28:31.180' AS DateTime), N'Admin', NULL, NULL, 0)
INSERT [dbo].[de_giay] ([id], [ma], [trang_thai], [ten], [ngay_tao], [nguoi_tao], [ngay_sua], [nguoi_sua], [deleted]) VALUES (2, N'DG002', N'Đang hoạt động', N'Đế Phylon', CAST(N'2024-10-15T00:28:31.180' AS DateTime), N'Admin', NULL, NULL, 0)
INSERT [dbo].[de_giay] ([id], [ma], [trang_thai], [ten], [ngay_tao], [nguoi_tao], [ngay_sua], [nguoi_sua], [deleted]) VALUES (3, N'DG003', N'Đang hoạt động', N'Đế EVA', CAST(N'2024-10-15T00:28:31.180' AS DateTime), N'Admin', NULL, NULL, 0)
SET IDENTITY_INSERT [dbo].[de_giay] OFF
GO
SET IDENTITY_INSERT [dbo].[dia_chi] ON 

INSERT [dbo].[dia_chi] ([id], [id_khach_hang], [tinh_thanh_pho], [quan_huyen], [phuong_xa], [dia_chi_cu_the], [mac_dinh], [ghi_chu]) VALUES (1, 1, N'Hà Nội', N'Huyện Phúc Thọ', N'Xã Xuân Đình', N'567 Xuân Đình', 1, N'')
INSERT [dbo].[dia_chi] ([id], [id_khach_hang], [tinh_thanh_pho], [quan_huyen], [phuong_xa], [dia_chi_cu_the], [mac_dinh], [ghi_chu]) VALUES (2, 2, N'Vĩnh Long', N'Huyện Vũng Liêm', N'Xã Trung Thành Tây', N'456 Đường Trung Thành Tây', 0, N'')
INSERT [dbo].[dia_chi] ([id], [id_khach_hang], [tinh_thanh_pho], [quan_huyen], [phuong_xa], [dia_chi_cu_the], [mac_dinh], [ghi_chu]) VALUES (3, 3, N'Bạc Liêu', N'Huyện Phước Long', N'Xã Phong Thạnh Tây A', N'789 Đường Phong Thạnh Tây A', 0, N'')
SET IDENTITY_INSERT [dbo].[dia_chi] OFF
GO
SET IDENTITY_INSERT [dbo].[gio_hang] ON 

INSERT [dbo].[gio_hang] ([id], [id_khach_hang]) VALUES (1, 1)
INSERT [dbo].[gio_hang] ([id], [id_khach_hang]) VALUES (2, 2)
INSERT [dbo].[gio_hang] ([id], [id_khach_hang]) VALUES (3, 3)
SET IDENTITY_INSERT [dbo].[gio_hang] OFF
GO
SET IDENTITY_INSERT [dbo].[gio_hang_chi_tiet] ON 

INSERT [dbo].[gio_hang_chi_tiet] ([id], [id_san_pham_chi_tiet], [id_gio_hang], [so_luong], [don_gia], [ngay_tao]) VALUES (1, 2, 1, 2, 2000000, CAST(N'2024-10-15T00:28:31.243' AS DateTime))
INSERT [dbo].[gio_hang_chi_tiet] ([id], [id_san_pham_chi_tiet], [id_gio_hang], [so_luong], [don_gia], [ngay_tao]) VALUES (2, 2, 2, 1, 2000000, CAST(N'2024-10-15T00:28:31.243' AS DateTime))
INSERT [dbo].[gio_hang_chi_tiet] ([id], [id_san_pham_chi_tiet], [id_gio_hang], [so_luong], [don_gia], [ngay_tao]) VALUES (3, 2, 3, 1, 2000000, CAST(N'2024-10-15T00:28:31.243' AS DateTime))
INSERT [dbo].[gio_hang_chi_tiet] ([id], [id_san_pham_chi_tiet], [id_gio_hang], [so_luong], [don_gia], [ngay_tao]) VALUES (5, 14, 1, 2, NULL, CAST(N'2024-10-27T21:42:25.200' AS DateTime))
INSERT [dbo].[gio_hang_chi_tiet] ([id], [id_san_pham_chi_tiet], [id_gio_hang], [so_luong], [don_gia], [ngay_tao]) VALUES (6, 11, 1, 1, NULL, CAST(N'2024-10-27T22:18:09.497' AS DateTime))
INSERT [dbo].[gio_hang_chi_tiet] ([id], [id_san_pham_chi_tiet], [id_gio_hang], [so_luong], [don_gia], [ngay_tao]) VALUES (7, 13, 1, 2, NULL, CAST(N'2024-10-28T00:05:15.763' AS DateTime))
INSERT [dbo].[gio_hang_chi_tiet] ([id], [id_san_pham_chi_tiet], [id_gio_hang], [so_luong], [don_gia], [ngay_tao]) VALUES (8, 8, 1, 3, NULL, CAST(N'2024-10-28T00:07:10.897' AS DateTime))
SET IDENTITY_INSERT [dbo].[gio_hang_chi_tiet] OFF
GO
SET IDENTITY_INSERT [dbo].[hinh_anh] ON 

INSERT [dbo].[hinh_anh] ([id], [id_san_pham_chi_tiet], [duong_dan], [trang_thai], [loai], [ngay_tao], [nguoi_tao], [ngay_sua], [nguoi_sua], [deleted]) VALUES (1, 1, N'https://res.cloudinary.com/drkrb9gk0/image/upload/v1723153319/9bd1c94f-a89a-4b43-8eaa-8936939083f9_shoestest.jpg', N'Đang hoạt động', N'image/png', CAST(N'2024-10-15T00:28:31.217' AS DateTime), N'Admin', NULL, NULL, 0)
INSERT [dbo].[hinh_anh] ([id], [id_san_pham_chi_tiet], [duong_dan], [trang_thai], [loai], [ngay_tao], [nguoi_tao], [ngay_sua], [nguoi_sua], [deleted]) VALUES (2, 2, N'https://res.cloudinary.com/drkrb9gk0/image/upload/v1723154011/09afc390-40e7-4975-b6b6-4b322e284b1e_shoestest2.jpg', N'Đang hoạt động', N'image/jpg', CAST(N'2024-10-15T00:28:31.217' AS DateTime), N'Admin', NULL, NULL, 0)
INSERT [dbo].[hinh_anh] ([id], [id_san_pham_chi_tiet], [duong_dan], [trang_thai], [loai], [ngay_tao], [nguoi_tao], [ngay_sua], [nguoi_sua], [deleted]) VALUES (3, 3, N'https://res.cloudinary.com/drkrb9gk0/image/upload/v1723154013/87e11848-10a8-45df-9b2b-e10d3713a508_shoetest1.jpg', N'Đang hoạt động', N'image/jpeg', CAST(N'2024-10-15T00:28:31.217' AS DateTime), N'Admin', NULL, NULL, 0)
INSERT [dbo].[hinh_anh] ([id], [id_san_pham_chi_tiet], [duong_dan], [trang_thai], [loai], [ngay_tao], [nguoi_tao], [ngay_sua], [nguoi_sua], [deleted]) VALUES (4, 4, N'http://res.cloudinary.com/drkrb9gk0/image/upload/v1728985909/ptm6zakrnqfugqgbyzv9.png', NULL, NULL, CAST(N'2024-10-15T16:52:39.997' AS DateTime), NULL, NULL, NULL, NULL)
INSERT [dbo].[hinh_anh] ([id], [id_san_pham_chi_tiet], [duong_dan], [trang_thai], [loai], [ngay_tao], [nguoi_tao], [ngay_sua], [nguoi_sua], [deleted]) VALUES (5, 4, N'http://res.cloudinary.com/drkrb9gk0/image/upload/v1728985909/k2ayu6r0narn0sln41ja.png', NULL, NULL, CAST(N'2024-10-15T16:52:40.010' AS DateTime), NULL, NULL, NULL, NULL)
INSERT [dbo].[hinh_anh] ([id], [id_san_pham_chi_tiet], [duong_dan], [trang_thai], [loai], [ngay_tao], [nguoi_tao], [ngay_sua], [nguoi_sua], [deleted]) VALUES (6, 5, N'http://res.cloudinary.com/drkrb9gk0/image/upload/v1728985909/ptm6zakrnqfugqgbyzv9.png', NULL, NULL, CAST(N'2024-10-15T16:52:40.017' AS DateTime), NULL, NULL, NULL, NULL)
INSERT [dbo].[hinh_anh] ([id], [id_san_pham_chi_tiet], [duong_dan], [trang_thai], [loai], [ngay_tao], [nguoi_tao], [ngay_sua], [nguoi_sua], [deleted]) VALUES (7, 5, N'http://res.cloudinary.com/drkrb9gk0/image/upload/v1728985909/k2ayu6r0narn0sln41ja.png', NULL, NULL, CAST(N'2024-10-15T16:52:40.020' AS DateTime), NULL, NULL, NULL, NULL)
INSERT [dbo].[hinh_anh] ([id], [id_san_pham_chi_tiet], [duong_dan], [trang_thai], [loai], [ngay_tao], [nguoi_tao], [ngay_sua], [nguoi_sua], [deleted]) VALUES (8, 11, N'http://res.cloudinary.com/drkrb9gk0/image/upload/v1730017543/a1gczqnr8hm0hd7euktx.jpg', NULL, NULL, CAST(N'2024-10-27T15:26:47.877' AS DateTime), NULL, NULL, NULL, NULL)
INSERT [dbo].[hinh_anh] ([id], [id_san_pham_chi_tiet], [duong_dan], [trang_thai], [loai], [ngay_tao], [nguoi_tao], [ngay_sua], [nguoi_sua], [deleted]) VALUES (9, 11, N'http://res.cloudinary.com/drkrb9gk0/image/upload/v1730017543/cygrmrrks5qk1vjvb6s7.jpg', NULL, NULL, CAST(N'2024-10-27T15:26:47.883' AS DateTime), NULL, NULL, NULL, NULL)
INSERT [dbo].[hinh_anh] ([id], [id_san_pham_chi_tiet], [duong_dan], [trang_thai], [loai], [ngay_tao], [nguoi_tao], [ngay_sua], [nguoi_sua], [deleted]) VALUES (10, 11, N'http://res.cloudinary.com/drkrb9gk0/image/upload/v1730017543/ojrum2magdktwz6uo9qj.jpg', NULL, NULL, CAST(N'2024-10-27T15:26:47.887' AS DateTime), NULL, NULL, NULL, NULL)
INSERT [dbo].[hinh_anh] ([id], [id_san_pham_chi_tiet], [duong_dan], [trang_thai], [loai], [ngay_tao], [nguoi_tao], [ngay_sua], [nguoi_sua], [deleted]) VALUES (14, 13, N'http://res.cloudinary.com/drkrb9gk0/image/upload/v1730017543/a1gczqnr8hm0hd7euktx.jpg', NULL, NULL, CAST(N'2024-10-27T15:26:47.893' AS DateTime), NULL, NULL, NULL, NULL)
INSERT [dbo].[hinh_anh] ([id], [id_san_pham_chi_tiet], [duong_dan], [trang_thai], [loai], [ngay_tao], [nguoi_tao], [ngay_sua], [nguoi_sua], [deleted]) VALUES (15, 13, N'http://res.cloudinary.com/drkrb9gk0/image/upload/v1730017543/cygrmrrks5qk1vjvb6s7.jpg', NULL, NULL, CAST(N'2024-10-27T15:26:47.893' AS DateTime), NULL, NULL, NULL, NULL)
INSERT [dbo].[hinh_anh] ([id], [id_san_pham_chi_tiet], [duong_dan], [trang_thai], [loai], [ngay_tao], [nguoi_tao], [ngay_sua], [nguoi_sua], [deleted]) VALUES (16, 13, N'http://res.cloudinary.com/drkrb9gk0/image/upload/v1730017543/ojrum2magdktwz6uo9qj.jpg', NULL, NULL, CAST(N'2024-10-27T15:26:47.897' AS DateTime), NULL, NULL, NULL, NULL)
INSERT [dbo].[hinh_anh] ([id], [id_san_pham_chi_tiet], [duong_dan], [trang_thai], [loai], [ngay_tao], [nguoi_tao], [ngay_sua], [nguoi_sua], [deleted]) VALUES (17, 8, N'http://res.cloudinary.com/drkrb9gk0/image/upload/v1730017547/tantbvzgsghlrlgonyrv.jpg', NULL, NULL, CAST(N'2024-10-27T15:26:51.470' AS DateTime), NULL, NULL, NULL, NULL)
INSERT [dbo].[hinh_anh] ([id], [id_san_pham_chi_tiet], [duong_dan], [trang_thai], [loai], [ngay_tao], [nguoi_tao], [ngay_sua], [nguoi_sua], [deleted]) VALUES (18, 8, N'http://res.cloudinary.com/drkrb9gk0/image/upload/v1730017547/gdjnchbroml3rvtbxowr.jpg', NULL, NULL, CAST(N'2024-10-27T15:26:51.470' AS DateTime), NULL, NULL, NULL, NULL)
INSERT [dbo].[hinh_anh] ([id], [id_san_pham_chi_tiet], [duong_dan], [trang_thai], [loai], [ngay_tao], [nguoi_tao], [ngay_sua], [nguoi_sua], [deleted]) VALUES (19, 8, N'http://res.cloudinary.com/drkrb9gk0/image/upload/v1730017547/kgskwbnruovxgdxwbnan.jpg', NULL, NULL, CAST(N'2024-10-27T15:26:51.473' AS DateTime), NULL, NULL, NULL, NULL)
INSERT [dbo].[hinh_anh] ([id], [id_san_pham_chi_tiet], [duong_dan], [trang_thai], [loai], [ngay_tao], [nguoi_tao], [ngay_sua], [nguoi_sua], [deleted]) VALUES (20, 9, N'http://res.cloudinary.com/drkrb9gk0/image/upload/v1730017547/tantbvzgsghlrlgonyrv.jpg', NULL, NULL, CAST(N'2024-10-27T15:26:51.477' AS DateTime), NULL, NULL, NULL, NULL)
INSERT [dbo].[hinh_anh] ([id], [id_san_pham_chi_tiet], [duong_dan], [trang_thai], [loai], [ngay_tao], [nguoi_tao], [ngay_sua], [nguoi_sua], [deleted]) VALUES (21, 9, N'http://res.cloudinary.com/drkrb9gk0/image/upload/v1730017547/gdjnchbroml3rvtbxowr.jpg', NULL, NULL, CAST(N'2024-10-27T15:26:51.477' AS DateTime), NULL, NULL, NULL, NULL)
INSERT [dbo].[hinh_anh] ([id], [id_san_pham_chi_tiet], [duong_dan], [trang_thai], [loai], [ngay_tao], [nguoi_tao], [ngay_sua], [nguoi_sua], [deleted]) VALUES (22, 9, N'http://res.cloudinary.com/drkrb9gk0/image/upload/v1730017547/kgskwbnruovxgdxwbnan.jpg', NULL, NULL, CAST(N'2024-10-27T15:26:51.477' AS DateTime), NULL, NULL, NULL, NULL)
INSERT [dbo].[hinh_anh] ([id], [id_san_pham_chi_tiet], [duong_dan], [trang_thai], [loai], [ngay_tao], [nguoi_tao], [ngay_sua], [nguoi_sua], [deleted]) VALUES (23, 10, N'http://res.cloudinary.com/drkrb9gk0/image/upload/v1730017547/tantbvzgsghlrlgonyrv.jpg', NULL, NULL, CAST(N'2024-10-27T15:26:51.483' AS DateTime), NULL, NULL, NULL, NULL)
INSERT [dbo].[hinh_anh] ([id], [id_san_pham_chi_tiet], [duong_dan], [trang_thai], [loai], [ngay_tao], [nguoi_tao], [ngay_sua], [nguoi_sua], [deleted]) VALUES (24, 10, N'http://res.cloudinary.com/drkrb9gk0/image/upload/v1730017547/gdjnchbroml3rvtbxowr.jpg', NULL, NULL, CAST(N'2024-10-27T15:26:51.483' AS DateTime), NULL, NULL, NULL, NULL)
INSERT [dbo].[hinh_anh] ([id], [id_san_pham_chi_tiet], [duong_dan], [trang_thai], [loai], [ngay_tao], [nguoi_tao], [ngay_sua], [nguoi_sua], [deleted]) VALUES (25, 10, N'http://res.cloudinary.com/drkrb9gk0/image/upload/v1730017547/kgskwbnruovxgdxwbnan.jpg', NULL, NULL, CAST(N'2024-10-27T15:26:51.487' AS DateTime), NULL, NULL, NULL, NULL)
INSERT [dbo].[hinh_anh] ([id], [id_san_pham_chi_tiet], [duong_dan], [trang_thai], [loai], [ngay_tao], [nguoi_tao], [ngay_sua], [nguoi_sua], [deleted]) VALUES (26, 14, N'http://res.cloudinary.com/drkrb9gk0/image/upload/v1730017552/uhsmmxdmxwdvmuthorfg.jpg', NULL, NULL, CAST(N'2024-10-27T15:26:56.560' AS DateTime), NULL, NULL, NULL, NULL)
INSERT [dbo].[hinh_anh] ([id], [id_san_pham_chi_tiet], [duong_dan], [trang_thai], [loai], [ngay_tao], [nguoi_tao], [ngay_sua], [nguoi_sua], [deleted]) VALUES (27, 14, N'http://res.cloudinary.com/drkrb9gk0/image/upload/v1730017552/qyyjal8c8uzmtludy13c.jpg', NULL, NULL, CAST(N'2024-10-27T15:26:56.567' AS DateTime), NULL, NULL, NULL, NULL)
INSERT [dbo].[hinh_anh] ([id], [id_san_pham_chi_tiet], [duong_dan], [trang_thai], [loai], [ngay_tao], [nguoi_tao], [ngay_sua], [nguoi_sua], [deleted]) VALUES (28, 14, N'http://res.cloudinary.com/drkrb9gk0/image/upload/v1730017552/k8ojp38qlns9fhy5v1dc.jpg', NULL, NULL, CAST(N'2024-10-27T15:26:56.567' AS DateTime), NULL, NULL, NULL, NULL)
INSERT [dbo].[hinh_anh] ([id], [id_san_pham_chi_tiet], [duong_dan], [trang_thai], [loai], [ngay_tao], [nguoi_tao], [ngay_sua], [nguoi_sua], [deleted]) VALUES (29, 15, N'http://res.cloudinary.com/drkrb9gk0/image/upload/v1730017552/uhsmmxdmxwdvmuthorfg.jpg', NULL, NULL, CAST(N'2024-10-27T15:26:56.573' AS DateTime), NULL, NULL, NULL, NULL)
INSERT [dbo].[hinh_anh] ([id], [id_san_pham_chi_tiet], [duong_dan], [trang_thai], [loai], [ngay_tao], [nguoi_tao], [ngay_sua], [nguoi_sua], [deleted]) VALUES (30, 15, N'http://res.cloudinary.com/drkrb9gk0/image/upload/v1730017552/qyyjal8c8uzmtludy13c.jpg', NULL, NULL, CAST(N'2024-10-27T15:26:56.577' AS DateTime), NULL, NULL, NULL, NULL)
INSERT [dbo].[hinh_anh] ([id], [id_san_pham_chi_tiet], [duong_dan], [trang_thai], [loai], [ngay_tao], [nguoi_tao], [ngay_sua], [nguoi_sua], [deleted]) VALUES (31, 15, N'http://res.cloudinary.com/drkrb9gk0/image/upload/v1730017552/k8ojp38qlns9fhy5v1dc.jpg', NULL, NULL, CAST(N'2024-10-27T15:26:56.577' AS DateTime), NULL, NULL, NULL, NULL)
INSERT [dbo].[hinh_anh] ([id], [id_san_pham_chi_tiet], [duong_dan], [trang_thai], [loai], [ngay_tao], [nguoi_tao], [ngay_sua], [nguoi_sua], [deleted]) VALUES (32, 16, N'http://res.cloudinary.com/drkrb9gk0/image/upload/v1730017552/uhsmmxdmxwdvmuthorfg.jpg', NULL, NULL, CAST(N'2024-10-27T15:26:56.580' AS DateTime), NULL, NULL, NULL, NULL)
INSERT [dbo].[hinh_anh] ([id], [id_san_pham_chi_tiet], [duong_dan], [trang_thai], [loai], [ngay_tao], [nguoi_tao], [ngay_sua], [nguoi_sua], [deleted]) VALUES (33, 16, N'http://res.cloudinary.com/drkrb9gk0/image/upload/v1730017552/qyyjal8c8uzmtludy13c.jpg', NULL, NULL, CAST(N'2024-10-27T15:26:56.583' AS DateTime), NULL, NULL, NULL, NULL)
INSERT [dbo].[hinh_anh] ([id], [id_san_pham_chi_tiet], [duong_dan], [trang_thai], [loai], [ngay_tao], [nguoi_tao], [ngay_sua], [nguoi_sua], [deleted]) VALUES (34, 16, N'http://res.cloudinary.com/drkrb9gk0/image/upload/v1730017552/k8ojp38qlns9fhy5v1dc.jpg', NULL, NULL, CAST(N'2024-10-27T15:26:56.583' AS DateTime), NULL, NULL, NULL, NULL)
INSERT [dbo].[hinh_anh] ([id], [id_san_pham_chi_tiet], [duong_dan], [trang_thai], [loai], [ngay_tao], [nguoi_tao], [ngay_sua], [nguoi_sua], [deleted]) VALUES (46, 27, N'http://res.cloudinary.com/drkrb9gk0/image/upload/v1731902055/w8oqlsrlvvep7bz60nrd.jpg', NULL, NULL, CAST(N'2024-11-18T10:55:46.377' AS DateTime), NULL, NULL, NULL, NULL)
INSERT [dbo].[hinh_anh] ([id], [id_san_pham_chi_tiet], [duong_dan], [trang_thai], [loai], [ngay_tao], [nguoi_tao], [ngay_sua], [nguoi_sua], [deleted]) VALUES (47, 27, N'http://res.cloudinary.com/drkrb9gk0/image/upload/v1731902055/kjtca6lyfd8hgoznnqje.jpg', NULL, NULL, CAST(N'2024-11-18T10:55:46.380' AS DateTime), NULL, NULL, NULL, NULL)
SET IDENTITY_INSERT [dbo].[hinh_anh] OFF
GO
SET IDENTITY_INSERT [dbo].[hinh_thuc_thanh_toan] ON 

INSERT [dbo].[hinh_thuc_thanh_toan] ([id], [ten], [loaigiaodich], [so_tien_thanh_toan], [trang_thai], [ngay_tao], [nguoi_tao], [ngay_sua], [nguoi_sua], [deleted], [vnpOrderInfo]) VALUES (1, N'VN Pay', N'Trả Trước', NULL, N'Đang hoạt động', CAST(N'2024-10-15T00:28:31.183' AS DateTime), N'Admin', NULL, NULL, 0, NULL)
INSERT [dbo].[hinh_thuc_thanh_toan] ([id], [ten], [loaigiaodich], [so_tien_thanh_toan], [trang_thai], [ngay_tao], [nguoi_tao], [ngay_sua], [nguoi_sua], [deleted], [vnpOrderInfo]) VALUES (2, N'Tiền mặt', N'Trả Sau', NULL, N'Đang hoạt động', CAST(N'2024-10-15T00:28:31.183' AS DateTime), N'Admin', NULL, NULL, 0, NULL)
SET IDENTITY_INSERT [dbo].[hinh_thuc_thanh_toan] OFF
GO
SET IDENTITY_INSERT [dbo].[hinh_thuc_thanh_toan_hoa_don] ON 

INSERT [dbo].[hinh_thuc_thanh_toan_hoa_don] ([id], [id_hinh_thuc_thanh_toan], [id_hoa_don]) VALUES (1, 1, 1)
INSERT [dbo].[hinh_thuc_thanh_toan_hoa_don] ([id], [id_hinh_thuc_thanh_toan], [id_hoa_don]) VALUES (2, 2, 2)
SET IDENTITY_INSERT [dbo].[hinh_thuc_thanh_toan_hoa_don] OFF
GO
SET IDENTITY_INSERT [dbo].[hoa_don] ON 

INSERT [dbo].[hoa_don] ([id], [id_nhan_vien], [id_phieu_giam_gia], [id_khach_hang], [ma], [tong_tien], [ten_nguoi_nhan], [so_dien_thoai], [tinh_thanh_pho], [quan_huyen], [phuong_xa], [dia_chi_cu_the], [phi_ship], [thoi_gian_nhan_du_kien], [loai_hoa_don], [ghi_chu], [ngay_tao], [nguoi_tao], [ngay_sua], [nguoi_sua], [trang_thai], [qr_image], [deleted]) VALUES (1, 1, 1, 1, N'HD001', 1000000, N'Nguyễn Văn A', N'0123456789', N'Hà Nội', N'Huyện Phúc Thọ', N'Xã Xuân Đình', N'123 Đường Xuân Đình', 50000, CAST(N'2024-07-06T10:00:00.000' AS DateTime), 0, NULL, CAST(N'2024-10-15T00:28:31.220' AS DateTime), N'Admin', CAST(N'2024-10-15T00:28:31.220' AS DateTime), N'Admin', N'Xác Nhận', NULL, 0)
INSERT [dbo].[hoa_don] ([id], [id_nhan_vien], [id_phieu_giam_gia], [id_khach_hang], [ma], [tong_tien], [ten_nguoi_nhan], [so_dien_thoai], [tinh_thanh_pho], [quan_huyen], [phuong_xa], [dia_chi_cu_the], [phi_ship], [thoi_gian_nhan_du_kien], [loai_hoa_don], [ghi_chu], [ngay_tao], [nguoi_tao], [ngay_sua], [nguoi_sua], [trang_thai], [qr_image], [deleted]) VALUES (2, 2, 2, 2, N'HD002', 2000000, N'Trần Thị B', N'0987654321', N'Vĩnh Long', N'Huyện Vũng Liêm', N'Xã Trung Thành Tây', N'456 Đường Trung Thành Tây', 30000, CAST(N'2024-07-07T15:00:00.000' AS DateTime), 1, NULL, CAST(N'2024-10-15T00:28:31.220' AS DateTime), N'Admin', CAST(N'2024-10-15T00:28:31.220' AS DateTime), N'Admin', N'Xác Nhận', NULL, 0)
INSERT [dbo].[hoa_don] ([id], [id_nhan_vien], [id_phieu_giam_gia], [id_khach_hang], [ma], [tong_tien], [ten_nguoi_nhan], [so_dien_thoai], [tinh_thanh_pho], [quan_huyen], [phuong_xa], [dia_chi_cu_the], [phi_ship], [thoi_gian_nhan_du_kien], [loai_hoa_don], [ghi_chu], [ngay_tao], [nguoi_tao], [ngay_sua], [nguoi_sua], [trang_thai], [qr_image], [deleted]) VALUES (3, 3, 3, 3, N'HD003', 1500000, N'Lê Văn C', N'0909123456', N'Bạc Liêu', N'Huyện Phước Long', N'Xã Phong Thạnh Tây A', N'789 Đường Phong Thạnh Tây A', 40000, CAST(N'2024-07-08T11:00:00.000' AS DateTime), 0, NULL, CAST(N'2024-10-15T00:28:31.220' AS DateTime), N'Admin', CAST(N'2024-10-15T00:28:31.220' AS DateTime), N'Admin', N'Xác Nhận', NULL, 0)
SET IDENTITY_INSERT [dbo].[hoa_don] OFF
GO
SET IDENTITY_INSERT [dbo].[hoa_don_chi_tiet] ON 

INSERT [dbo].[hoa_don_chi_tiet] ([id], [id_hoa_don], [id_san_pham_chi_tiet], [ma], [so_luong], [don_gia], [ngay_tao], [nguoi_tao], [ngay_sua], [nguoi_sua], [deleted]) VALUES (1, 1, 1, N'HDCT001', 2, 500000, CAST(N'2024-10-15T00:28:31.230' AS DateTime), N'Admin', CAST(N'2024-10-15T00:28:31.230' AS DateTime), N'Admin', 0)
INSERT [dbo].[hoa_don_chi_tiet] ([id], [id_hoa_don], [id_san_pham_chi_tiet], [ma], [so_luong], [don_gia], [ngay_tao], [nguoi_tao], [ngay_sua], [nguoi_sua], [deleted]) VALUES (2, 2, 2, N'HDCT002', 1, 500000, CAST(N'2024-10-15T00:28:31.230' AS DateTime), N'Admin', CAST(N'2024-10-15T00:28:31.230' AS DateTime), N'Admin', 0)
INSERT [dbo].[hoa_don_chi_tiet] ([id], [id_hoa_don], [id_san_pham_chi_tiet], [ma], [so_luong], [don_gia], [ngay_tao], [nguoi_tao], [ngay_sua], [nguoi_sua], [deleted]) VALUES (3, 3, 3, N'HDCT003', 3, 666666.67, CAST(N'2024-10-15T00:28:31.230' AS DateTime), N'Admin', CAST(N'2024-10-15T00:28:31.230' AS DateTime), N'Admin', 0)
SET IDENTITY_INSERT [dbo].[hoa_don_chi_tiet] OFF
GO
SET IDENTITY_INSERT [dbo].[khach_hang] ON 

INSERT [dbo].[khach_hang] ([id], [ma], [ten], [mat_khau], [email], [so_dien_thoai], [ngay_sinh], [gioi_tinh], [trang_thai], [vai_tro], [ngay_tao], [nguoi_tao], [ngay_sua], [nguoi_sua], [hinh_anh], [deleted], [otpQuenMatKhau]) VALUES (1, N'KH001', N'Nguyễn Văn A', N'$2a$10$WC5tH4eNIjBUOI5mvxIl6eVKidWsiMPu5rEOXJdpimWTr5eHmilOu', N'nguyenvana@gmail.com', N'0987654321', CAST(N'1990-01-01' AS Date), 1, N'Đang hoạt động', N'Khách hàng', CAST(N'2024-10-15T00:00:00.000' AS DateTime), N'Admin', NULL, NULL, N'https://res.cloudinary.com/drkrb9gk0/image/upload/v1726575846/anh-nobita_amjlfd.jpg', 0, NULL)
INSERT [dbo].[khach_hang] ([id], [ma], [ten], [mat_khau], [email], [so_dien_thoai], [ngay_sinh], [gioi_tinh], [trang_thai], [vai_tro], [ngay_tao], [nguoi_tao], [ngay_sua], [nguoi_sua], [hinh_anh], [deleted], [otpQuenMatKhau]) VALUES (2, N'KH002', N'Trần Thị B', N'$2a$10$p2omiKdwyc2EgNmYKFkCPe0lf4RdgwupY3Iqrf08kNWddXO7hEpBS', N'tranthib@gmail.com', N'0987654322', CAST(N'1995-02-02' AS Date), 0, N'Đang hoạt động', N'Khách hàng', CAST(N'2024-10-15T00:00:00.000' AS DateTime), N'Admin', NULL, NULL, N'https://res.cloudinary.com/drkrb9gk0/image/upload/v1726576007/anh-dekhi_rhmepa.jpg', 0, NULL)
INSERT [dbo].[khach_hang] ([id], [ma], [ten], [mat_khau], [email], [so_dien_thoai], [ngay_sinh], [gioi_tinh], [trang_thai], [vai_tro], [ngay_tao], [nguoi_tao], [ngay_sua], [nguoi_sua], [hinh_anh], [deleted], [otpQuenMatKhau]) VALUES (3, N'KH003', N'Lê Văn C', N'$2a$10$0FtKsviEmJx7aPFcLFU46uv3mceN6.mW3qRqxmXWWlAhgDaGzSEgC', N'levanc@gmail.com', N'0987654323', CAST(N'2000-03-03' AS Date), 1, N'Đang hoạt động', N'Khách hàng', CAST(N'2024-10-15T00:00:00.000' AS DateTime), N'Admin', NULL, NULL, N'https://res.cloudinary.com/drkrb9gk0/image/upload/v1726576007/anh-doraemon_qjshir.jpg', 0, NULL)
SET IDENTITY_INSERT [dbo].[khach_hang] OFF
GO
SET IDENTITY_INSERT [dbo].[khach_hang_phieu_giam_gia] ON 

INSERT [dbo].[khach_hang_phieu_giam_gia] ([id], [id_khach_hang], [id_phieu_giam_gia]) VALUES (1, 1, 1)
INSERT [dbo].[khach_hang_phieu_giam_gia] ([id], [id_khach_hang], [id_phieu_giam_gia]) VALUES (2, 2, 2)
INSERT [dbo].[khach_hang_phieu_giam_gia] ([id], [id_khach_hang], [id_phieu_giam_gia]) VALUES (3, 1, 16)
SET IDENTITY_INSERT [dbo].[khach_hang_phieu_giam_gia] OFF
GO
SET IDENTITY_INSERT [dbo].[lich_su_hoa_don] ON 

INSERT [dbo].[lich_su_hoa_don] ([id], [id_khach_hang], [id_nhan_vien], [id_hoa_don], [hanh_dong], [ngay_tao], [nguoi_tao], [ngay_sua], [nguoi_sua], [deleted]) VALUES (1, 1, 1, 1, N'Tạo mới', CAST(N'2024-10-15T00:28:31.230' AS DateTime), N'Admin', NULL, NULL, 0)
INSERT [dbo].[lich_su_hoa_don] ([id], [id_khach_hang], [id_nhan_vien], [id_hoa_don], [hanh_dong], [ngay_tao], [nguoi_tao], [ngay_sua], [nguoi_sua], [deleted]) VALUES (2, 1, 1, 1, N'Xác Nhận', CAST(N'2024-10-15T00:28:31.230' AS DateTime), N'Admin', NULL, NULL, 0)
INSERT [dbo].[lich_su_hoa_don] ([id], [id_khach_hang], [id_nhan_vien], [id_hoa_don], [hanh_dong], [ngay_tao], [nguoi_tao], [ngay_sua], [nguoi_sua], [deleted]) VALUES (3, 2, 2, 2, N'Tạo mới', CAST(N'2024-10-15T00:28:31.230' AS DateTime), N'Admin', NULL, NULL, 0)
INSERT [dbo].[lich_su_hoa_don] ([id], [id_khach_hang], [id_nhan_vien], [id_hoa_don], [hanh_dong], [ngay_tao], [nguoi_tao], [ngay_sua], [nguoi_sua], [deleted]) VALUES (4, 2, 2, 2, N'Xác Nhận', CAST(N'2024-10-15T00:28:31.230' AS DateTime), N'Admin', NULL, NULL, 0)
INSERT [dbo].[lich_su_hoa_don] ([id], [id_khach_hang], [id_nhan_vien], [id_hoa_don], [hanh_dong], [ngay_tao], [nguoi_tao], [ngay_sua], [nguoi_sua], [deleted]) VALUES (5, 3, 3, 3, N'Tạo mới', CAST(N'2024-10-15T00:28:31.230' AS DateTime), N'Admin', NULL, NULL, 0)
INSERT [dbo].[lich_su_hoa_don] ([id], [id_khach_hang], [id_nhan_vien], [id_hoa_don], [hanh_dong], [ngay_tao], [nguoi_tao], [ngay_sua], [nguoi_sua], [deleted]) VALUES (6, 3, 3, 3, N'Xác Nhận', CAST(N'2024-10-15T00:28:31.230' AS DateTime), N'Admin', NULL, NULL, 0)
SET IDENTITY_INSERT [dbo].[lich_su_hoa_don] OFF
GO
SET IDENTITY_INSERT [dbo].[mau_sac] ON 

INSERT [dbo].[mau_sac] ([id], [ma], [trang_thai], [ten], [ngay_tao], [nguoi_tao], [ngay_sua], [nguoi_sua], [deleted]) VALUES (1, N'MS001', N'Đang hoạt động', N'Đen', CAST(N'2024-10-15T00:28:31.183' AS DateTime), N'Admin', NULL, NULL, 0)
INSERT [dbo].[mau_sac] ([id], [ma], [trang_thai], [ten], [ngay_tao], [nguoi_tao], [ngay_sua], [nguoi_sua], [deleted]) VALUES (2, N'MS002', N'Đang hoạt động', N'Trắng', CAST(N'2024-10-15T00:28:31.183' AS DateTime), N'Admin', NULL, NULL, 0)
INSERT [dbo].[mau_sac] ([id], [ma], [trang_thai], [ten], [ngay_tao], [nguoi_tao], [ngay_sua], [nguoi_sua], [deleted]) VALUES (3, N'MS003', N'Đang hoạt động', N'Đỏ', CAST(N'2024-10-15T00:28:31.183' AS DateTime), N'Admin', NULL, NULL, 0)
SET IDENTITY_INSERT [dbo].[mau_sac] OFF
GO
SET IDENTITY_INSERT [dbo].[nha_san_xuat] ON 

INSERT [dbo].[nha_san_xuat] ([id], [ma], [trang_thai], [ten], [ngay_tao], [nguoi_tao], [ngay_sua], [nguoi_sua], [deleted]) VALUES (1, N'NSX001', N'Đang hoạt động', N'Việt Nam', CAST(N'2024-10-15T00:28:31.183' AS DateTime), N'Admin', NULL, NULL, 0)
INSERT [dbo].[nha_san_xuat] ([id], [ma], [trang_thai], [ten], [ngay_tao], [nguoi_tao], [ngay_sua], [nguoi_sua], [deleted]) VALUES (2, N'NSX002', N'Đang hoạt động', N'Trung Quốc', CAST(N'2024-10-15T00:28:31.183' AS DateTime), N'Admin', NULL, NULL, 0)
INSERT [dbo].[nha_san_xuat] ([id], [ma], [trang_thai], [ten], [ngay_tao], [nguoi_tao], [ngay_sua], [nguoi_sua], [deleted]) VALUES (3, N'NSX003', N'Đang hoạt động', N'Thái Lan', CAST(N'2024-10-15T00:28:31.183' AS DateTime), N'Admin', NULL, NULL, 0)
SET IDENTITY_INSERT [dbo].[nha_san_xuat] OFF
GO
SET IDENTITY_INSERT [dbo].[nhan_vien] ON 

INSERT [dbo].[nhan_vien] ([id], [ma], [ten], [mat_khau], [hinh_anh], [email], [so_dien_thoai], [ngay_sinh], [tinh_thanh_pho], [quan_huyen], [phuong_xa], [dia_chi_cu_the], [gioi_tinh], [trang_thai], [vai_tro], [ngay_tao], [nguoi_tao], [ngay_sua], [nguoi_sua], [deleted]) VALUES (1, N'NV001', N'Phạm Thị D', N'7C4A8D09CA3762AF61E59520943DC26494F8941B', N'https://res.cloudinary.com/drkrb9gk0/image/upload/v1726556120/5b05fb8d-b203-4421-bc49-cd0516fc955b_user%20default.jpg', N'phamthid@gmail.com', N'0987654324', CAST(N'1988-04-04' AS Date), N'Hà Nội', N'Hoàn Kiếm', N'Phường Hàng Bạc', N'123 Đường Hàng Bạc', 0, N'Đang làm việc', N'Nhân viên bán hàng', CAST(N'2024-10-15T00:28:31.180' AS DateTime), N'Admin', NULL, NULL, 0)
INSERT [dbo].[nhan_vien] ([id], [ma], [ten], [mat_khau], [hinh_anh], [email], [so_dien_thoai], [ngay_sinh], [tinh_thanh_pho], [quan_huyen], [phuong_xa], [dia_chi_cu_the], [gioi_tinh], [trang_thai], [vai_tro], [ngay_tao], [nguoi_tao], [ngay_sua], [nguoi_sua], [deleted]) VALUES (2, N'NV002', N'Phạm Thị C', N'7C4A8D09CA3762AF61E59520943DC26494F8941B', N'https://res.cloudinary.com/drkrb9gk0/image/upload/v1726556120/5b05fb8d-b203-4421-bc49-cd0516fc955b_user%20default.jpg', N'phamthid@gmail.com', N'0987654324', CAST(N'1988-04-04' AS Date), N'Hà Nội', N'Hoàn Kiếm', N'Phường Hàng Bạc', N'123 Đường Hàng Bạc', 0, N'Đang làm việc', N'Nhân viên bán hàng', CAST(N'2024-10-15T00:28:31.180' AS DateTime), N'Admin', NULL, NULL, 0)
INSERT [dbo].[nhan_vien] ([id], [ma], [ten], [mat_khau], [hinh_anh], [email], [so_dien_thoai], [ngay_sinh], [tinh_thanh_pho], [quan_huyen], [phuong_xa], [dia_chi_cu_the], [gioi_tinh], [trang_thai], [vai_tro], [ngay_tao], [nguoi_tao], [ngay_sua], [nguoi_sua], [deleted]) VALUES (3, N'NV003', N'Phạm Thị E', N'7C4A8D09CA3762AF61E59520943DC26494F8941B', N'https://res.cloudinary.com/drkrb9gk0/image/upload/v1726556120/5b05fb8d-b203-4421-bc49-cd0516fc955b_user%20default.jpg', N'phamthid@gmail.com', N'0987654324', CAST(N'1988-04-04' AS Date), N'Hà Nội', N'Hoàn Kiếm', N'Phường Hàng Bạc', N'123 Đường Hàng Bạc', 0, N'Đang làm việc', N'ROLE_ADMIN', CAST(N'2024-10-15T00:28:31.180' AS DateTime), N'Admin', NULL, NULL, 0)
INSERT [dbo].[nhan_vien] ([id], [ma], [ten], [mat_khau], [hinh_anh], [email], [so_dien_thoai], [ngay_sinh], [tinh_thanh_pho], [quan_huyen], [phuong_xa], [dia_chi_cu_the], [gioi_tinh], [trang_thai], [vai_tro], [ngay_tao], [nguoi_tao], [ngay_sua], [nguoi_sua], [deleted]) VALUES (4, N'NV003', N'Phạm Thị Quan', N'$2a$10$mgw0ee4c0G1VeY0G3LkQ9OS39bp9OPLEBPXlDbdTcNL/4P4vRTpWO', N'https://res.cloudinary.com/drkrb9gk0/image/upload/v1726556120/5b05fb8d-b203-4421-bc49-cd0516fc955b_user%20default.jpg', N'quan123@gmail.com', N'0987654324', CAST(N'1988-04-04' AS Date), N'Hà Nội', N'Hoàn Kiếm', N'Phường Hàng Bạc', N'123 Đường Hàng Bạc', 0, N'Đang làm việc', N'ROLE_ADMIN', CAST(N'2024-10-15T00:28:31.180' AS DateTime), N'Admin', NULL, NULL, 0)
SET IDENTITY_INSERT [dbo].[nhan_vien] OFF
GO
SET IDENTITY_INSERT [dbo].[phieu_giam_gia] ON 

INSERT [dbo].[phieu_giam_gia] ([id], [ma], [trang_thai], [so_luong], [ten], [mo_ta], [ngay_bat_dau], [ngay_ket_thuc], [hinh_thuc_giam], [gia_tri_don_toi_thieu], [giam_toi_da], [so_tien_giam], [phan_tram_giam], [hinh_thuc_su_dung], [ngay_tao], [nguoi_tao], [ngay_sua], [nguoi_sua], [deleted], [block]) VALUES (1, N'GG001', N'Kết thúc', 20, N'Giảm giá 10%', N'Áp dụng cho đơn hàng từ 500.000đ', CAST(N'2023-04-01T00:00:00.000' AS DateTime), CAST(N'2023-04-30T00:00:00.000' AS DateTime), 0, 500000, NULL, 100000, NULL, 0, CAST(N'2024-10-15T00:28:31.180' AS DateTime), N'Admin', NULL, NULL, 0, NULL)
INSERT [dbo].[phieu_giam_gia] ([id], [ma], [trang_thai], [so_luong], [ten], [mo_ta], [ngay_bat_dau], [ngay_ket_thuc], [hinh_thuc_giam], [gia_tri_don_toi_thieu], [giam_toi_da], [so_tien_giam], [phan_tram_giam], [hinh_thuc_su_dung], [ngay_tao], [nguoi_tao], [ngay_sua], [nguoi_sua], [deleted], [block]) VALUES (2, N'GG002', N'Kết thúc', 30, N'Giảm giá 20%', N'Áp dụng cho đơn hàng từ 1.000.000đ', CAST(N'2023-05-01T00:00:00.000' AS DateTime), CAST(N'2023-05-31T00:00:00.000' AS DateTime), 1, 1000000, 200000, NULL, 20, 1, CAST(N'2024-10-15T00:28:31.180' AS DateTime), N'Admin', NULL, NULL, 0, NULL)
INSERT [dbo].[phieu_giam_gia] ([id], [ma], [trang_thai], [so_luong], [ten], [mo_ta], [ngay_bat_dau], [ngay_ket_thuc], [hinh_thuc_giam], [gia_tri_don_toi_thieu], [giam_toi_da], [so_tien_giam], [phan_tram_giam], [hinh_thuc_su_dung], [ngay_tao], [nguoi_tao], [ngay_sua], [nguoi_sua], [deleted], [block]) VALUES (3, N'GG003', N'Kết thúc', 40, N'Giảm giá 50%', N'Áp dụng cho tất cả đơn hàng', CAST(N'2023-06-01T00:00:00.000' AS DateTime), CAST(N'2023-06-30T00:00:00.000' AS DateTime), 1, 0, 500000, NULL, 50, 0, CAST(N'2024-10-15T00:28:31.180' AS DateTime), N'Admin', NULL, NULL, 1, NULL)
INSERT [dbo].[phieu_giam_gia] ([id], [ma], [trang_thai], [so_luong], [ten], [mo_ta], [ngay_bat_dau], [ngay_ket_thuc], [hinh_thuc_giam], [gia_tri_don_toi_thieu], [giam_toi_da], [so_tien_giam], [phan_tram_giam], [hinh_thuc_su_dung], [ngay_tao], [nguoi_tao], [ngay_sua], [nguoi_sua], [deleted], [block]) VALUES (4, N'PGG-T69RKJC', N'Kết thúc', 123, N'Giảm 50%', NULL, CAST(N'2024-10-17T16:39:00.000' AS DateTime), CAST(N'2024-10-22T16:39:00.000' AS DateTime), 1, 123123, 200, NULL, 92, 0, CAST(N'2024-10-17T16:40:02.287' AS DateTime), N'Admin', CAST(N'2024-10-17T16:40:02.287' AS DateTime), NULL, 0, NULL)
INSERT [dbo].[phieu_giam_gia] ([id], [ma], [trang_thai], [so_luong], [ten], [mo_ta], [ngay_bat_dau], [ngay_ket_thuc], [hinh_thuc_giam], [gia_tri_don_toi_thieu], [giam_toi_da], [so_tien_giam], [phan_tram_giam], [hinh_thuc_su_dung], [ngay_tao], [nguoi_tao], [ngay_sua], [nguoi_sua], [deleted], [block]) VALUES (5, N'PGG-I2VWKHI', N'Kết thúc', 123, N'213', NULL, CAST(N'2024-10-17T16:40:00.000' AS DateTime), CAST(N'2024-10-29T16:40:00.000' AS DateTime), 1, 123, 123, NULL, 123, 0, CAST(N'2024-10-17T16:40:14.013' AS DateTime), N'Admin', CAST(N'2024-10-17T16:40:14.013' AS DateTime), NULL, 0, NULL)
INSERT [dbo].[phieu_giam_gia] ([id], [ma], [trang_thai], [so_luong], [ten], [mo_ta], [ngay_bat_dau], [ngay_ket_thuc], [hinh_thuc_giam], [gia_tri_don_toi_thieu], [giam_toi_da], [so_tien_giam], [phan_tram_giam], [hinh_thuc_su_dung], [ngay_tao], [nguoi_tao], [ngay_sua], [nguoi_sua], [deleted], [block]) VALUES (6, N'PGG-QDCGK7H', N'Kết thúc', 31, N'123', NULL, CAST(N'2024-10-17T16:40:00.000' AS DateTime), CAST(N'2024-10-25T16:40:00.000' AS DateTime), 1, 212, 1231, NULL, 123, 0, CAST(N'2024-10-17T16:40:23.683' AS DateTime), N'Admin', CAST(N'2024-10-17T16:40:23.683' AS DateTime), NULL, 0, NULL)
INSERT [dbo].[phieu_giam_gia] ([id], [ma], [trang_thai], [so_luong], [ten], [mo_ta], [ngay_bat_dau], [ngay_ket_thuc], [hinh_thuc_giam], [gia_tri_don_toi_thieu], [giam_toi_da], [so_tien_giam], [phan_tram_giam], [hinh_thuc_su_dung], [ngay_tao], [nguoi_tao], [ngay_sua], [nguoi_sua], [deleted], [block]) VALUES (7, N'PGG-CHB9QVX', N'Kết thúc', 1, N'qweq', NULL, CAST(N'2024-10-17T17:03:00.000' AS DateTime), CAST(N'2024-10-24T17:03:00.000' AS DateTime), 1, 111, 23123, NULL, 15, 0, CAST(N'2024-10-17T17:04:06.747' AS DateTime), N'Admin', CAST(N'2024-10-17T17:04:06.747' AS DateTime), NULL, 0, NULL)
INSERT [dbo].[phieu_giam_gia] ([id], [ma], [trang_thai], [so_luong], [ten], [mo_ta], [ngay_bat_dau], [ngay_ket_thuc], [hinh_thuc_giam], [gia_tri_don_toi_thieu], [giam_toi_da], [so_tien_giam], [phan_tram_giam], [hinh_thuc_su_dung], [ngay_tao], [nguoi_tao], [ngay_sua], [nguoi_sua], [deleted], [block]) VALUES (8, N'PGG-0ANVTVM', N'Kết thúc', 1, N'Giảm 50%', NULL, CAST(N'2024-10-19T17:13:00.000' AS DateTime), CAST(N'2024-10-30T17:13:00.000' AS DateTime), 1, 3123123, 12312, NULL, 50, 0, CAST(N'2024-10-17T17:13:18.107' AS DateTime), N'Admin', CAST(N'2024-10-17T17:13:18.107' AS DateTime), NULL, 0, 0)
INSERT [dbo].[phieu_giam_gia] ([id], [ma], [trang_thai], [so_luong], [ten], [mo_ta], [ngay_bat_dau], [ngay_ket_thuc], [hinh_thuc_giam], [gia_tri_don_toi_thieu], [giam_toi_da], [so_tien_giam], [phan_tram_giam], [hinh_thuc_su_dung], [ngay_tao], [nguoi_tao], [ngay_sua], [nguoi_sua], [deleted], [block]) VALUES (9, N'PGG-KBWS50V', N'Kết thúc', 312, N'Giảm 50%', NULL, CAST(N'2024-10-26T17:40:00.000' AS DateTime), CAST(N'2024-10-31T17:40:00.000' AS DateTime), 1, 312, 12312, NULL, 50, 0, CAST(N'2024-10-17T17:40:33.133' AS DateTime), N'Admin', CAST(N'2024-10-17T17:40:33.133' AS DateTime), NULL, 0, 0)
INSERT [dbo].[phieu_giam_gia] ([id], [ma], [trang_thai], [so_luong], [ten], [mo_ta], [ngay_bat_dau], [ngay_ket_thuc], [hinh_thuc_giam], [gia_tri_don_toi_thieu], [giam_toi_da], [so_tien_giam], [phan_tram_giam], [hinh_thuc_su_dung], [ngay_tao], [nguoi_tao], [ngay_sua], [nguoi_sua], [deleted], [block]) VALUES (10, N'PGG-BSJXDR3', N'Kết thúc', 123, N'123', NULL, CAST(N'2024-10-17T19:35:00.000' AS DateTime), CAST(N'2024-10-26T19:34:00.000' AS DateTime), 0, 12312, 3123, 123, NULL, 0, CAST(N'2024-10-17T19:35:05.733' AS DateTime), N'Admin', CAST(N'2024-10-17T19:35:05.733' AS DateTime), NULL, 0, NULL)
INSERT [dbo].[phieu_giam_gia] ([id], [ma], [trang_thai], [so_luong], [ten], [mo_ta], [ngay_bat_dau], [ngay_ket_thuc], [hinh_thuc_giam], [gia_tri_don_toi_thieu], [giam_toi_da], [so_tien_giam], [phan_tram_giam], [hinh_thuc_su_dung], [ngay_tao], [nguoi_tao], [ngay_sua], [nguoi_sua], [deleted], [block]) VALUES (11, N'PGG-321L95K', N'Kết thúc', 123, N'Giảm 50%', NULL, CAST(N'2024-10-17T19:40:00.000' AS DateTime), CAST(N'2024-10-23T19:39:00.000' AS DateTime), 0, 3123, 12312, 123123, NULL, 0, CAST(N'2024-10-17T19:39:17.323' AS DateTime), N'Admin', CAST(N'2024-10-17T19:39:17.323' AS DateTime), NULL, 0, NULL)
INSERT [dbo].[phieu_giam_gia] ([id], [ma], [trang_thai], [so_luong], [ten], [mo_ta], [ngay_bat_dau], [ngay_ket_thuc], [hinh_thuc_giam], [gia_tri_don_toi_thieu], [giam_toi_da], [so_tien_giam], [phan_tram_giam], [hinh_thuc_su_dung], [ngay_tao], [nguoi_tao], [ngay_sua], [nguoi_sua], [deleted], [block]) VALUES (12, N'PGG-NMY7ZA6', N'Kết thúc', 123, N'123123', NULL, CAST(N'2024-10-17T19:45:00.000' AS DateTime), CAST(N'2024-10-31T19:44:00.000' AS DateTime), 0, 123, 123, 1231123, NULL, 0, CAST(N'2024-10-17T19:44:13.677' AS DateTime), N'Admin', CAST(N'2024-10-17T19:44:13.677' AS DateTime), NULL, 0, 0)
INSERT [dbo].[phieu_giam_gia] ([id], [ma], [trang_thai], [so_luong], [ten], [mo_ta], [ngay_bat_dau], [ngay_ket_thuc], [hinh_thuc_giam], [gia_tri_don_toi_thieu], [giam_toi_da], [so_tien_giam], [phan_tram_giam], [hinh_thuc_su_dung], [ngay_tao], [nguoi_tao], [ngay_sua], [nguoi_sua], [deleted], [block]) VALUES (13, N'PGG-POS4FIE', N'Đang diễn ra', 31231, N'Giảm 50%', NULL, CAST(N'2024-11-16T13:19:00.000' AS DateTime), CAST(N'2024-11-30T13:19:00.000' AS DateTime), 1, 12312, 200, NULL, 92, 0, CAST(N'2024-11-18T13:19:27.000' AS DateTime), N'Admin', CAST(N'2024-11-18T13:19:27.000' AS DateTime), NULL, 0, 0)
INSERT [dbo].[phieu_giam_gia] ([id], [ma], [trang_thai], [so_luong], [ten], [mo_ta], [ngay_bat_dau], [ngay_ket_thuc], [hinh_thuc_giam], [gia_tri_don_toi_thieu], [giam_toi_da], [so_tien_giam], [phan_tram_giam], [hinh_thuc_su_dung], [ngay_tao], [nguoi_tao], [ngay_sua], [nguoi_sua], [deleted], [block]) VALUES (14, N'PGG-UU0RPPR', N'Kết thúc', 10, N'Giảm 50%', NULL, CAST(N'2024-11-18T15:05:00.000' AS DateTime), CAST(N'2024-11-21T15:05:00.000' AS DateTime), 1, 10000, 100000, NULL, 10, 0, CAST(N'2024-11-18T15:06:32.390' AS DateTime), N'Admin', CAST(N'2024-11-18T15:06:32.390' AS DateTime), NULL, 0, NULL)
INSERT [dbo].[phieu_giam_gia] ([id], [ma], [trang_thai], [so_luong], [ten], [mo_ta], [ngay_bat_dau], [ngay_ket_thuc], [hinh_thuc_giam], [gia_tri_don_toi_thieu], [giam_toi_da], [so_tien_giam], [phan_tram_giam], [hinh_thuc_su_dung], [ngay_tao], [nguoi_tao], [ngay_sua], [nguoi_sua], [deleted], [block]) VALUES (15, N'PGG-00ZCBDF', N'Kết thúc', 15, N'Giảm 500k', NULL, CAST(N'2024-11-17T15:07:00.000' AS DateTime), CAST(N'2024-11-21T15:07:00.000' AS DateTime), 0, 5000000, NULL, 500000, NULL, 0, CAST(N'2024-11-18T15:07:10.477' AS DateTime), N'Admin', CAST(N'2024-11-18T15:07:10.477' AS DateTime), NULL, 0, NULL)
INSERT [dbo].[phieu_giam_gia] ([id], [ma], [trang_thai], [so_luong], [ten], [mo_ta], [ngay_bat_dau], [ngay_ket_thuc], [hinh_thuc_giam], [gia_tri_don_toi_thieu], [giam_toi_da], [so_tien_giam], [phan_tram_giam], [hinh_thuc_su_dung], [ngay_tao], [nguoi_tao], [ngay_sua], [nguoi_sua], [deleted], [block]) VALUES (16, N'PGG-9BD9Q06', N'Kết thúc', 100, N'Giảm 50%', NULL, CAST(N'2024-11-18T16:51:00.000' AS DateTime), CAST(N'2024-11-21T16:51:00.000' AS DateTime), 0, 10000, NULL, 100000, NULL, 1, CAST(N'2024-11-18T16:51:28.467' AS DateTime), N'Admin', CAST(N'2024-11-18T16:51:28.467' AS DateTime), NULL, 0, NULL)
SET IDENTITY_INSERT [dbo].[phieu_giam_gia] OFF
GO
SET IDENTITY_INSERT [dbo].[san_pham] ON 

INSERT [dbo].[san_pham] ([id], [ma], [trang_thai], [id_co_giay], [id_de_giay], [id_danh_muc], [id_thuong_hieu], [ten], [ghi_chu], [ngay_tao], [nguoi_tao], [ngay_sua], [nguoi_sua], [deleted]) VALUES (1, N'SP001', N'Đang hoạt động', 1, 1, 1, 1, N'Giày Nike Air Force 1', NULL, CAST(N'2024-10-15T00:28:31.183' AS DateTime), N'Admin', NULL, NULL, 0)
INSERT [dbo].[san_pham] ([id], [ma], [trang_thai], [id_co_giay], [id_de_giay], [id_danh_muc], [id_thuong_hieu], [ten], [ghi_chu], [ngay_tao], [nguoi_tao], [ngay_sua], [nguoi_sua], [deleted]) VALUES (2, N'SP002', N'Đang hoạt động', 2, 2, 2, 2, N'Giày Adidas Ultra Boost', NULL, CAST(N'2024-10-15T00:28:31.183' AS DateTime), N'Admin', NULL, NULL, 0)
INSERT [dbo].[san_pham] ([id], [ma], [trang_thai], [id_co_giay], [id_de_giay], [id_danh_muc], [id_thuong_hieu], [ten], [ghi_chu], [ngay_tao], [nguoi_tao], [ngay_sua], [nguoi_sua], [deleted]) VALUES (3, N'SP003', N'Đang hoạt động', 3, 3, 3, 3, N'Giày Puma Suede Classic', NULL, CAST(N'2024-10-15T00:28:31.183' AS DateTime), N'Admin', NULL, NULL, 0)
INSERT [dbo].[san_pham] ([id], [ma], [trang_thai], [id_co_giay], [id_de_giay], [id_danh_muc], [id_thuong_hieu], [ten], [ghi_chu], [ngay_tao], [nguoi_tao], [ngay_sua], [nguoi_sua], [deleted]) VALUES (4, N'SP-81BKB3K', N'Đang hoạt động', 3, 1, 3, 1, N'Giảm 50%', N'1', CAST(N'2024-10-15T16:52:12.897' AS DateTime), N'Admin', NULL, NULL, 0)
INSERT [dbo].[san_pham] ([id], [ma], [trang_thai], [id_co_giay], [id_de_giay], [id_danh_muc], [id_thuong_hieu], [ten], [ghi_chu], [ngay_tao], [nguoi_tao], [ngay_sua], [nguoi_sua], [deleted]) VALUES (5, N'SP-ZUYVHUA', N'Đang hoạt động', 1, 1, 1, 1, N'Giày Bitits ', N'', CAST(N'2024-10-27T15:24:14.957' AS DateTime), N'Admin', NULL, NULL, 0)
INSERT [dbo].[san_pham] ([id], [ma], [trang_thai], [id_co_giay], [id_de_giay], [id_danh_muc], [id_thuong_hieu], [ten], [ghi_chu], [ngay_tao], [nguoi_tao], [ngay_sua], [nguoi_sua], [deleted]) VALUES (8, N'SP-K84JXDA', N'Hết hàng', 1, 1, 2, 1, N'Giảm 50%', N'', CAST(N'2024-11-18T14:54:53.040' AS DateTime), N'Admin', NULL, NULL, 0)
INSERT [dbo].[san_pham] ([id], [ma], [trang_thai], [id_co_giay], [id_de_giay], [id_danh_muc], [id_thuong_hieu], [ten], [ghi_chu], [ngay_tao], [nguoi_tao], [ngay_sua], [nguoi_sua], [deleted]) VALUES (9, N'SP-E15ZV5H', N'Hết hàng', 2, 1, 3, 2, N'Giảm 50%', N'', CAST(N'2024-11-18T14:56:05.830' AS DateTime), N'Admin', NULL, NULL, 0)
SET IDENTITY_INSERT [dbo].[san_pham] OFF
GO
SET IDENTITY_INSERT [dbo].[san_pham_chi_tiet] ON 

INSERT [dbo].[san_pham_chi_tiet] ([id], [id_san_pham], [id_xuat_xu], [id_nha_san_xuat], [id_chat_lieu], [id_size], [id_mau_sac], [ma], [trang_thai], [don_gia], [mo_ta], [so_luong], [khoi_luong], [don_vi_tinh], [ghi_chu], [ngay_tao], [nguoi_tao], [ngay_sua], [nguoi_sua], [qr_image], [deleted]) VALUES (1, 1, 1, 1, 1, 1, 1, N'SPCT001', N'Đang hoạt động', 1500000, N'Giày Nike Air Force 1 màu Đen size 38', 100, N'500', N'gram', NULL, CAST(N'2024-10-15T00:28:31.207' AS DateTime), N'Admin', NULL, NULL, NULL, 0)
INSERT [dbo].[san_pham_chi_tiet] ([id], [id_san_pham], [id_xuat_xu], [id_nha_san_xuat], [id_chat_lieu], [id_size], [id_mau_sac], [ma], [trang_thai], [don_gia], [mo_ta], [so_luong], [khoi_luong], [don_vi_tinh], [ghi_chu], [ngay_tao], [nguoi_tao], [ngay_sua], [nguoi_sua], [qr_image], [deleted]) VALUES (2, 2, 2, 2, 2, 2, 2, N'SPCT002', N'Đang hoạt động', 2000000, N'Giày Adidas Ultra Boost màu trắng size 39', 50, N'600', N'gram', NULL, CAST(N'2024-10-15T00:28:31.207' AS DateTime), N'Admin', NULL, NULL, NULL, 0)
INSERT [dbo].[san_pham_chi_tiet] ([id], [id_san_pham], [id_xuat_xu], [id_nha_san_xuat], [id_chat_lieu], [id_size], [id_mau_sac], [ma], [trang_thai], [don_gia], [mo_ta], [so_luong], [khoi_luong], [don_vi_tinh], [ghi_chu], [ngay_tao], [nguoi_tao], [ngay_sua], [nguoi_sua], [qr_image], [deleted]) VALUES (3, 3, 3, 3, 3, 3, 3, N'SPCT003', N'Đang hoạt động', 1800000, N'35236236', 123, N'700', N'gram', N'123', CAST(N'2024-10-15T00:28:31.207' AS DateTime), N'Admin', NULL, NULL, NULL, 0)
INSERT [dbo].[san_pham_chi_tiet] ([id], [id_san_pham], [id_xuat_xu], [id_nha_san_xuat], [id_chat_lieu], [id_size], [id_mau_sac], [ma], [trang_thai], [don_gia], [mo_ta], [so_luong], [khoi_luong], [don_vi_tinh], [ghi_chu], [ngay_tao], [nguoi_tao], [ngay_sua], [nguoi_sua], [qr_image], [deleted]) VALUES (4, 4, 3, 1, 3, 2, 2, N'SPCT-R7UZO56', N'Ngừng hoạt động', 90000, N'123', 123123, N'600', N'gram', N'141241', CAST(N'2024-10-15T16:52:12.943' AS DateTime), N'Admin', NULL, NULL, N'http://res.cloudinary.com/dcoppcfvi/image/upload/v1728985889/uqkgfduktolitj0xxwkk.gif', 0)
INSERT [dbo].[san_pham_chi_tiet] ([id], [id_san_pham], [id_xuat_xu], [id_nha_san_xuat], [id_chat_lieu], [id_size], [id_mau_sac], [ma], [trang_thai], [don_gia], [mo_ta], [so_luong], [khoi_luong], [don_vi_tinh], [ghi_chu], [ngay_tao], [nguoi_tao], [ngay_sua], [nguoi_sua], [qr_image], [deleted]) VALUES (5, 4, 1, 1, 1, 3, 2, N'SPCT-NP5FBA6', N'Đang hoạt động', 123, NULL, 123123, N'400', N'gram', NULL, CAST(N'2024-10-15T16:52:12.957' AS DateTime), N'Admin', NULL, NULL, N'http://res.cloudinary.com/dcoppcfvi/image/upload/v1728985889/b4xl57k5ngmit7bep8fm.gif', 0)
INSERT [dbo].[san_pham_chi_tiet] ([id], [id_san_pham], [id_xuat_xu], [id_nha_san_xuat], [id_chat_lieu], [id_size], [id_mau_sac], [ma], [trang_thai], [don_gia], [mo_ta], [so_luong], [khoi_luong], [don_vi_tinh], [ghi_chu], [ngay_tao], [nguoi_tao], [ngay_sua], [nguoi_sua], [qr_image], [deleted]) VALUES (8, 5, 2, 1, 1, 1, 1, N'SPCT-BCMRLW7', N'Đang hoạt động', 1000000, N'', 25, N'400', N'gram', N'', CAST(N'2024-10-27T15:24:15.023' AS DateTime), N'Admin', NULL, NULL, N'http://res.cloudinary.com/drkrb9gk0/image/upload/v1730017570/uaihngfgwvtubxzokwwp.gif', 0)
INSERT [dbo].[san_pham_chi_tiet] ([id], [id_san_pham], [id_xuat_xu], [id_nha_san_xuat], [id_chat_lieu], [id_size], [id_mau_sac], [ma], [trang_thai], [don_gia], [mo_ta], [so_luong], [khoi_luong], [don_vi_tinh], [ghi_chu], [ngay_tao], [nguoi_tao], [ngay_sua], [nguoi_sua], [qr_image], [deleted]) VALUES (9, 5, 2, 1, 1, 2, 1, N'SPCT-CU5HSUY', N'Đang hoạt động', 1000000, NULL, 25, N'400', N'gram', NULL, CAST(N'2024-10-27T15:24:15.047' AS DateTime), N'Admin', NULL, NULL, N'http://res.cloudinary.com/drkrb9gk0/image/upload/v1730017570/xqsoumkim1zkmq50p1ck.gif', 0)
INSERT [dbo].[san_pham_chi_tiet] ([id], [id_san_pham], [id_xuat_xu], [id_nha_san_xuat], [id_chat_lieu], [id_size], [id_mau_sac], [ma], [trang_thai], [don_gia], [mo_ta], [so_luong], [khoi_luong], [don_vi_tinh], [ghi_chu], [ngay_tao], [nguoi_tao], [ngay_sua], [nguoi_sua], [qr_image], [deleted]) VALUES (10, 5, 2, 1, 1, 3, 1, N'SPCT-GNFQ5N8', N'Đang hoạt động', 1000000, NULL, 25, N'400', N'gram', NULL, CAST(N'2024-10-27T15:24:15.053' AS DateTime), N'Admin', NULL, NULL, N'http://res.cloudinary.com/drkrb9gk0/image/upload/v1730017570/vfewdaeab8ekbuppbrfm.gif', 0)
INSERT [dbo].[san_pham_chi_tiet] ([id], [id_san_pham], [id_xuat_xu], [id_nha_san_xuat], [id_chat_lieu], [id_size], [id_mau_sac], [ma], [trang_thai], [don_gia], [mo_ta], [so_luong], [khoi_luong], [don_vi_tinh], [ghi_chu], [ngay_tao], [nguoi_tao], [ngay_sua], [nguoi_sua], [qr_image], [deleted]) VALUES (11, 5, 2, 1, 1, 1, 2, N'SPCT-0ME6D5T', N'Đang hoạt động', 1000000, NULL, 25, N'400', N'gram', NULL, CAST(N'2024-10-27T15:24:15.063' AS DateTime), N'Admin', NULL, NULL, N'http://res.cloudinary.com/drkrb9gk0/image/upload/v1730017570/mzvppqmcnwgtnmb5sbm6.gif', 0)
INSERT [dbo].[san_pham_chi_tiet] ([id], [id_san_pham], [id_xuat_xu], [id_nha_san_xuat], [id_chat_lieu], [id_size], [id_mau_sac], [ma], [trang_thai], [don_gia], [mo_ta], [so_luong], [khoi_luong], [don_vi_tinh], [ghi_chu], [ngay_tao], [nguoi_tao], [ngay_sua], [nguoi_sua], [qr_image], [deleted]) VALUES (13, 5, 2, 1, 1, 3, 2, N'SPCT-2K63A8P', N'Đang hoạt động', 1000000, NULL, 25, N'400', N'gram', NULL, CAST(N'2024-10-27T15:24:15.077' AS DateTime), N'Admin', NULL, NULL, N'http://res.cloudinary.com/drkrb9gk0/image/upload/v1730017570/tvxwef8oils4erj2fhno.gif', 0)
INSERT [dbo].[san_pham_chi_tiet] ([id], [id_san_pham], [id_xuat_xu], [id_nha_san_xuat], [id_chat_lieu], [id_size], [id_mau_sac], [ma], [trang_thai], [don_gia], [mo_ta], [so_luong], [khoi_luong], [don_vi_tinh], [ghi_chu], [ngay_tao], [nguoi_tao], [ngay_sua], [nguoi_sua], [qr_image], [deleted]) VALUES (14, 5, 2, 1, 1, 1, 3, N'SPCT-FF9153I', N'Đang hoạt động', 1000000, NULL, 25, N'400', N'gram', NULL, CAST(N'2024-10-27T15:24:15.083' AS DateTime), N'Admin', NULL, NULL, N'http://res.cloudinary.com/drkrb9gk0/image/upload/v1730017570/iy2usfuk9zusqt3qnykm.gif', 0)
INSERT [dbo].[san_pham_chi_tiet] ([id], [id_san_pham], [id_xuat_xu], [id_nha_san_xuat], [id_chat_lieu], [id_size], [id_mau_sac], [ma], [trang_thai], [don_gia], [mo_ta], [so_luong], [khoi_luong], [don_vi_tinh], [ghi_chu], [ngay_tao], [nguoi_tao], [ngay_sua], [nguoi_sua], [qr_image], [deleted]) VALUES (15, 5, 2, 1, 1, 2, 3, N'SPCT-RI97UCB', N'Đang hoạt động', 1000000, NULL, 25, N'400', N'gram', NULL, CAST(N'2024-10-27T15:24:15.090' AS DateTime), N'Admin', NULL, NULL, N'http://res.cloudinary.com/drkrb9gk0/image/upload/v1730017570/uwzbvwoqknxxo2p2r4wy.gif', 0)
INSERT [dbo].[san_pham_chi_tiet] ([id], [id_san_pham], [id_xuat_xu], [id_nha_san_xuat], [id_chat_lieu], [id_size], [id_mau_sac], [ma], [trang_thai], [don_gia], [mo_ta], [so_luong], [khoi_luong], [don_vi_tinh], [ghi_chu], [ngay_tao], [nguoi_tao], [ngay_sua], [nguoi_sua], [qr_image], [deleted]) VALUES (16, 5, 2, 1, 1, 3, 3, N'SPCT-P021QNQ', N'Đang hoạt động', 1000000, NULL, 25, N'400', N'gram', NULL, CAST(N'2024-10-27T15:24:15.097' AS DateTime), N'Admin', NULL, NULL, N'http://res.cloudinary.com/drkrb9gk0/image/upload/v1730017571/nkuhpq8gqkcpuos1fuzf.gif', 0)
INSERT [dbo].[san_pham_chi_tiet] ([id], [id_san_pham], [id_xuat_xu], [id_nha_san_xuat], [id_chat_lieu], [id_size], [id_mau_sac], [ma], [trang_thai], [don_gia], [mo_ta], [so_luong], [khoi_luong], [don_vi_tinh], [ghi_chu], [ngay_tao], [nguoi_tao], [ngay_sua], [nguoi_sua], [qr_image], [deleted]) VALUES (27, 5, 1, 1, 1, 1, 1, N'SPCT-IVQOR38', N'Đang hoạt động', 111, NULL, 111, NULL, NULL, NULL, CAST(N'2024-11-18T10:55:44.633' AS DateTime), N'Admin', CAST(N'2024-11-18T10:55:44.633' AS DateTime), NULL, NULL, 0)
INSERT [dbo].[san_pham_chi_tiet] ([id], [id_san_pham], [id_xuat_xu], [id_nha_san_xuat], [id_chat_lieu], [id_size], [id_mau_sac], [ma], [trang_thai], [don_gia], [mo_ta], [so_luong], [khoi_luong], [don_vi_tinh], [ghi_chu], [ngay_tao], [nguoi_tao], [ngay_sua], [nguoi_sua], [qr_image], [deleted]) VALUES (30, 8, 1, 1, 1, 2, 2, N'SPCT-6GNCIJ3', N'Hết hàng', 0, NULL, 0, N'400', N'gram', NULL, CAST(N'2024-11-18T14:54:53.060' AS DateTime), N'Admin', NULL, NULL, NULL, 0)
INSERT [dbo].[san_pham_chi_tiet] ([id], [id_san_pham], [id_xuat_xu], [id_nha_san_xuat], [id_chat_lieu], [id_size], [id_mau_sac], [ma], [trang_thai], [don_gia], [mo_ta], [so_luong], [khoi_luong], [don_vi_tinh], [ghi_chu], [ngay_tao], [nguoi_tao], [ngay_sua], [nguoi_sua], [qr_image], [deleted]) VALUES (31, 8, 1, 1, 1, 3, 2, N'SPCT-5OB1Q1B', N'Hết hàng', 0, NULL, 0, N'400', N'gram', NULL, CAST(N'2024-11-18T14:54:53.073' AS DateTime), N'Admin', NULL, NULL, NULL, 0)
INSERT [dbo].[san_pham_chi_tiet] ([id], [id_san_pham], [id_xuat_xu], [id_nha_san_xuat], [id_chat_lieu], [id_size], [id_mau_sac], [ma], [trang_thai], [don_gia], [mo_ta], [so_luong], [khoi_luong], [don_vi_tinh], [ghi_chu], [ngay_tao], [nguoi_tao], [ngay_sua], [nguoi_sua], [qr_image], [deleted]) VALUES (32, 8, 1, 1, 1, 2, 3, N'SPCT-OO2RWE9', N'Hết hàng', 0, NULL, 0, N'400', N'gram', NULL, CAST(N'2024-11-18T14:54:53.080' AS DateTime), N'Admin', NULL, NULL, NULL, 0)
INSERT [dbo].[san_pham_chi_tiet] ([id], [id_san_pham], [id_xuat_xu], [id_nha_san_xuat], [id_chat_lieu], [id_size], [id_mau_sac], [ma], [trang_thai], [don_gia], [mo_ta], [so_luong], [khoi_luong], [don_vi_tinh], [ghi_chu], [ngay_tao], [nguoi_tao], [ngay_sua], [nguoi_sua], [qr_image], [deleted]) VALUES (33, 8, 1, 1, 1, 3, 3, N'SPCT-G93HS4D', N'Hết hàng', 0, NULL, 0, N'400', N'gram', NULL, CAST(N'2024-11-18T14:54:53.083' AS DateTime), N'Admin', NULL, NULL, NULL, 0)
INSERT [dbo].[san_pham_chi_tiet] ([id], [id_san_pham], [id_xuat_xu], [id_nha_san_xuat], [id_chat_lieu], [id_size], [id_mau_sac], [ma], [trang_thai], [don_gia], [mo_ta], [so_luong], [khoi_luong], [don_vi_tinh], [ghi_chu], [ngay_tao], [nguoi_tao], [ngay_sua], [nguoi_sua], [qr_image], [deleted]) VALUES (34, 9, 2, 2, 1, 2, 2, N'SPCT-EC9KU9S', N'Hết hàng', 0, NULL, 0, N'400', N'gram', NULL, CAST(N'2024-11-18T14:56:05.870' AS DateTime), N'Admin', NULL, NULL, NULL, 0)
INSERT [dbo].[san_pham_chi_tiet] ([id], [id_san_pham], [id_xuat_xu], [id_nha_san_xuat], [id_chat_lieu], [id_size], [id_mau_sac], [ma], [trang_thai], [don_gia], [mo_ta], [so_luong], [khoi_luong], [don_vi_tinh], [ghi_chu], [ngay_tao], [nguoi_tao], [ngay_sua], [nguoi_sua], [qr_image], [deleted]) VALUES (35, 9, 2, 2, 1, 3, 2, N'SPCT-THU5VFS', N'Hết hàng', 0, NULL, 0, N'400', N'gram', NULL, CAST(N'2024-11-18T14:56:05.887' AS DateTime), N'Admin', NULL, NULL, NULL, 0)
INSERT [dbo].[san_pham_chi_tiet] ([id], [id_san_pham], [id_xuat_xu], [id_nha_san_xuat], [id_chat_lieu], [id_size], [id_mau_sac], [ma], [trang_thai], [don_gia], [mo_ta], [so_luong], [khoi_luong], [don_vi_tinh], [ghi_chu], [ngay_tao], [nguoi_tao], [ngay_sua], [nguoi_sua], [qr_image], [deleted]) VALUES (36, 9, 2, 2, 1, 2, 3, N'SPCT-V2VKQN2', N'Hết hàng', 0, NULL, 0, N'400', N'gram', NULL, CAST(N'2024-11-18T14:56:05.897' AS DateTime), N'Admin', NULL, NULL, NULL, 0)
INSERT [dbo].[san_pham_chi_tiet] ([id], [id_san_pham], [id_xuat_xu], [id_nha_san_xuat], [id_chat_lieu], [id_size], [id_mau_sac], [ma], [trang_thai], [don_gia], [mo_ta], [so_luong], [khoi_luong], [don_vi_tinh], [ghi_chu], [ngay_tao], [nguoi_tao], [ngay_sua], [nguoi_sua], [qr_image], [deleted]) VALUES (37, 9, 2, 2, 1, 3, 3, N'SPCT-8CMD0YN', N'Hết hàng', 0, NULL, 0, N'400', N'gram', NULL, CAST(N'2024-11-18T14:56:05.910' AS DateTime), N'Admin', NULL, NULL, NULL, 0)
SET IDENTITY_INSERT [dbo].[san_pham_chi_tiet] OFF
GO
SET IDENTITY_INSERT [dbo].[size] ON 

INSERT [dbo].[size] ([id], [ma], [trang_thai], [ten], [ngay_tao], [nguoi_tao], [ngay_sua], [nguoi_sua], [deleted]) VALUES (1, N'S001', N'Đang hoạt động', N'38', CAST(N'2024-10-15T00:28:31.183' AS DateTime), N'Admin', NULL, NULL, 0)
INSERT [dbo].[size] ([id], [ma], [trang_thai], [ten], [ngay_tao], [nguoi_tao], [ngay_sua], [nguoi_sua], [deleted]) VALUES (2, N'S002', N'Đang hoạt động', N'39', CAST(N'2024-10-15T00:28:31.183' AS DateTime), N'Admin', NULL, NULL, 0)
INSERT [dbo].[size] ([id], [ma], [trang_thai], [ten], [ngay_tao], [nguoi_tao], [ngay_sua], [nguoi_sua], [deleted]) VALUES (3, N'S003', N'Đang hoạt động', N'40', CAST(N'2024-10-15T00:28:31.183' AS DateTime), N'Admin', NULL, NULL, 0)
SET IDENTITY_INSERT [dbo].[size] OFF
GO
SET IDENTITY_INSERT [dbo].[thuong_hieu] ON 

INSERT [dbo].[thuong_hieu] ([id], [ma], [trang_thai], [ten], [ngay_tao], [nguoi_tao], [ngay_sua], [nguoi_sua], [deleted]) VALUES (1, N'TH001', N'Đang hoạt động', N'Nike', CAST(N'2024-10-15T00:28:31.180' AS DateTime), N'Admin', NULL, NULL, 0)
INSERT [dbo].[thuong_hieu] ([id], [ma], [trang_thai], [ten], [ngay_tao], [nguoi_tao], [ngay_sua], [nguoi_sua], [deleted]) VALUES (2, N'TH002', N'Đang hoạt động', N'Adidas', CAST(N'2024-10-15T00:28:31.180' AS DateTime), N'Admin', NULL, NULL, 0)
INSERT [dbo].[thuong_hieu] ([id], [ma], [trang_thai], [ten], [ngay_tao], [nguoi_tao], [ngay_sua], [nguoi_sua], [deleted]) VALUES (3, N'TH003', N'Đang hoạt động', N'Puma', CAST(N'2024-10-15T00:28:31.180' AS DateTime), N'Admin', NULL, NULL, 0)
SET IDENTITY_INSERT [dbo].[thuong_hieu] OFF
GO
SET IDENTITY_INSERT [dbo].[xuat_xu] ON 

INSERT [dbo].[xuat_xu] ([id], [ma], [trang_thai], [ten], [ngay_tao], [nguoi_tao], [ngay_sua], [nguoi_sua], [deleted]) VALUES (1, N'XX001', N'Đang hoạt động', N'Việt Nam', CAST(N'2024-10-15T00:28:31.183' AS DateTime), N'Admin', NULL, NULL, 0)
INSERT [dbo].[xuat_xu] ([id], [ma], [trang_thai], [ten], [ngay_tao], [nguoi_tao], [ngay_sua], [nguoi_sua], [deleted]) VALUES (2, N'XX002', N'Đang hoạt động', N'Trung Quốc', CAST(N'2024-10-15T00:28:31.183' AS DateTime), N'Admin', NULL, NULL, 0)
INSERT [dbo].[xuat_xu] ([id], [ma], [trang_thai], [ten], [ngay_tao], [nguoi_tao], [ngay_sua], [nguoi_sua], [deleted]) VALUES (3, N'XX003', N'Đang hoạt động', N'Thái Lan', CAST(N'2024-10-15T00:28:31.183' AS DateTime), N'Admin', NULL, NULL, 0)
SET IDENTITY_INSERT [dbo].[xuat_xu] OFF
GO
ALTER TABLE [dbo].[dia_chi]  WITH CHECK ADD FOREIGN KEY([id_khach_hang])
REFERENCES [dbo].[khach_hang] ([id])
GO
ALTER TABLE [dbo].[gio_hang]  WITH CHECK ADD FOREIGN KEY([id_khach_hang])
REFERENCES [dbo].[khach_hang] ([id])
GO
ALTER TABLE [dbo].[gio_hang_chi_tiet]  WITH CHECK ADD FOREIGN KEY([id_gio_hang])
REFERENCES [dbo].[gio_hang] ([id])
GO
ALTER TABLE [dbo].[gio_hang_chi_tiet]  WITH CHECK ADD FOREIGN KEY([id_san_pham_chi_tiet])
REFERENCES [dbo].[san_pham_chi_tiet] ([id])
GO
ALTER TABLE [dbo].[hinh_anh]  WITH CHECK ADD FOREIGN KEY([id_san_pham_chi_tiet])
REFERENCES [dbo].[san_pham_chi_tiet] ([id])
GO
ALTER TABLE [dbo].[hinh_thuc_thanh_toan_hoa_don]  WITH CHECK ADD FOREIGN KEY([id_hinh_thuc_thanh_toan])
REFERENCES [dbo].[hinh_thuc_thanh_toan] ([id])
GO
ALTER TABLE [dbo].[hinh_thuc_thanh_toan_hoa_don]  WITH CHECK ADD FOREIGN KEY([id_hoa_don])
REFERENCES [dbo].[hoa_don] ([id])
GO
ALTER TABLE [dbo].[hoa_don]  WITH CHECK ADD FOREIGN KEY([id_khach_hang])
REFERENCES [dbo].[khach_hang] ([id])
GO
ALTER TABLE [dbo].[hoa_don]  WITH CHECK ADD FOREIGN KEY([id_nhan_vien])
REFERENCES [dbo].[nhan_vien] ([id])
GO
ALTER TABLE [dbo].[hoa_don]  WITH CHECK ADD FOREIGN KEY([id_phieu_giam_gia])
REFERENCES [dbo].[phieu_giam_gia] ([id])
GO
ALTER TABLE [dbo].[hoa_don_chi_tiet]  WITH CHECK ADD FOREIGN KEY([id_hoa_don])
REFERENCES [dbo].[hoa_don] ([id])
GO
ALTER TABLE [dbo].[hoa_don_chi_tiet]  WITH CHECK ADD FOREIGN KEY([id_san_pham_chi_tiet])
REFERENCES [dbo].[san_pham_chi_tiet] ([id])
GO
ALTER TABLE [dbo].[khach_hang_phieu_giam_gia]  WITH CHECK ADD FOREIGN KEY([id_khach_hang])
REFERENCES [dbo].[khach_hang] ([id])
GO
ALTER TABLE [dbo].[khach_hang_phieu_giam_gia]  WITH CHECK ADD FOREIGN KEY([id_phieu_giam_gia])
REFERENCES [dbo].[phieu_giam_gia] ([id])
GO
ALTER TABLE [dbo].[lich_su_hoa_don]  WITH CHECK ADD FOREIGN KEY([id_hoa_don])
REFERENCES [dbo].[hoa_don] ([id])
GO
ALTER TABLE [dbo].[lich_su_hoa_don]  WITH CHECK ADD FOREIGN KEY([id_khach_hang])
REFERENCES [dbo].[khach_hang] ([id])
GO
ALTER TABLE [dbo].[lich_su_hoa_don]  WITH CHECK ADD FOREIGN KEY([id_nhan_vien])
REFERENCES [dbo].[nhan_vien] ([id])
GO
ALTER TABLE [dbo].[san_pham]  WITH CHECK ADD FOREIGN KEY([id_co_giay])
REFERENCES [dbo].[co_giay] ([id])
GO
ALTER TABLE [dbo].[san_pham]  WITH CHECK ADD FOREIGN KEY([id_danh_muc])
REFERENCES [dbo].[danh_muc] ([id])
GO
ALTER TABLE [dbo].[san_pham]  WITH CHECK ADD FOREIGN KEY([id_de_giay])
REFERENCES [dbo].[de_giay] ([id])
GO
ALTER TABLE [dbo].[san_pham]  WITH CHECK ADD FOREIGN KEY([id_thuong_hieu])
REFERENCES [dbo].[thuong_hieu] ([id])
GO
ALTER TABLE [dbo].[san_pham_chi_tiet]  WITH CHECK ADD FOREIGN KEY([id_chat_lieu])
REFERENCES [dbo].[chat_lieu] ([id])
GO
ALTER TABLE [dbo].[san_pham_chi_tiet]  WITH CHECK ADD FOREIGN KEY([id_mau_sac])
REFERENCES [dbo].[mau_sac] ([id])
GO
ALTER TABLE [dbo].[san_pham_chi_tiet]  WITH CHECK ADD FOREIGN KEY([id_nha_san_xuat])
REFERENCES [dbo].[nha_san_xuat] ([id])
GO
ALTER TABLE [dbo].[san_pham_chi_tiet]  WITH CHECK ADD FOREIGN KEY([id_san_pham])
REFERENCES [dbo].[san_pham] ([id])
GO
ALTER TABLE [dbo].[san_pham_chi_tiet]  WITH CHECK ADD FOREIGN KEY([id_size])
REFERENCES [dbo].[size] ([id])
GO
ALTER TABLE [dbo].[san_pham_chi_tiet]  WITH CHECK ADD FOREIGN KEY([id_xuat_xu])
REFERENCES [dbo].[xuat_xu] ([id])
GO
USE [master]
GO
ALTER DATABASE [DATN_CHINSHOES] SET  READ_WRITE 
GO
