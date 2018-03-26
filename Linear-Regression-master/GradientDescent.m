function [w, Js] = GradientDescent(X, y, w, alpha, iterations)
    % Prepare Variables
    m = length(y);
    Js = zeros(iterations, 1);
    
    for i = 1 : iterations,
        h = X * w;
        t1 = w(1) - (alpha * (1 / m) * sum((h - y) .* X(:, 1)));
        t2 = w(2) - (alpha * (1 / m) * sum((h - y) .* X(:, 2)));
        t3 = w(3) - (alpha * (1 / m) * sum((h - y) .* X(:, 3)));
        t4 = w(4) - (alpha * (1 / m) * sum((h - y) .* X(:, 4)));
        t5 = w(5) - (alpha * (1 / m) * sum((h - y) .* X(:, 5)));
%        t6 = w(6) - (alpha * (1 / m) * sum((h - y) .* X(:, 6)));
%        t7 = w(7) - (alpha * (1 / m) * sum((h - y) .* X(:, 7)));
        w(1) = t1;
        w(2) = t2;
        w(3) = t3;
        w(4) = t4;
        w(5) = t5;
%        w(6) = t6;
%        w(7) = t7;
        Js(i) = ComputeCost(X, y, w);
    end
end