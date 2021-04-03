<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<!-- 모바일에서 사이트가 PC에서의 픽셀크기 기준으로 작동하게 하기(반응형 하려면 필요) -->
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">

<!-- daisyui 불러오기 -->
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/daisyui@0.14.4/dist/full.css">

<!-- 폰트어썸 불러오기 -->
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.2/css/all.min.css">

<!-- 제이쿼리 -->
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>

<!-- vue -->
<script src="https://unpkg.com/vue@next"></script>

<link rel="stylesheet" href="/resource/common.css" />
<script src="/resource/common.js" defer></script>
<title>INSTA</title>
</head>
<body>
	<div class="site-wrap">
		<header class="top-bar top-bar--fly">
			<div class="container mx-auto flex h-full">
				<a href="#" class="px-3 flex items-center">
					<span>
						<i class="fas fa-lemon"></i>
					</span>
					<span class="hidden sm:block">&nbsp;INSTA</span>
				</a>

				<div class="flex-grow"></div>

				<nav class="menu-box-1">
					<ul class="flex h-full">
						<li>
							<a href="#" class="h-full flex items-center px-5">
								<span>
									<i class="fas fa-home"></i>
								</span>
								<span class="hidden sm:block">&nbsp;HOME</span>
							</a>
						</li>
						<li>
							<a href="#" class="h-full flex items-center px-5">
								<span>
									<i class="far fa-newspaper"></i>
								</span>
								<span class="hidden md:block">&nbsp;BOARD</span>
							</a>
							<div>
								<h1>
									<a href="#">
										<span>
											<i class="far fa-newspaper"></i>
										</span>
										<span>&nbsp;BOARD</span>
									</a>
								</h1>
								<ul>
									<li>
										<a href="#">
											<span>
												<i class="far fa-clipboard"></i>
											</span>
											<span>&nbsp;NOTICE</span>
										</a>
									</li>
									<li>
										<a href="#">
											<span>
												<i class="far fa-clipboard"></i>
											</span>
											<span>&nbsp;FREE</span>
										</a>
									</li>
								</ul>
							</div>
						</li>
						<li>
							<a href="#" class="h-full flex items-center px-5">
								<span>
									<i class="fas fa-sign-out-alt"></i>
								</span>
								<span class="hidden md:block">&nbsp;SIGN OUT</span>
							</a>
							<div></div>
						</li>
					</ul>
				</nav>
			</div>

		</header>
	</div>
</body>
</html>